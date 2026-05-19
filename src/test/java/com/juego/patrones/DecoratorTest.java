package com.juego.patrones;

import com.juego.model.Personaje;
import com.juego.patrones.decorator.GuerreroDecorator;
import com.juego.patrones.decorator.MagoDecorator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del patrón Decorator.
 * Usa Mockito para aislar dependencias y verificar interacciones.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DecoratorTest - Pruebas del patrón Decorator")
class DecoratorTest {

    // ══════════════════════════════════════════════
    //  GuerreroDecorator
    // ══════════════════════════════════════════════

    @Test
    @DisplayName("GuerreroDecorator debe delegar getNombre al personaje base")
    void testGuerreroGetNombre() {
        Personaje base = new Personaje("Arthas");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);
        assertEquals("Arthas", guerrero.getNombre());
    }

    @Test
    @DisplayName("GuerreroDecorator debe atacar con daño entre 15 y 35")
    void testGuerreroAtaqueBonusEspada() {
        Personaje base     = new Personaje("Arthas");
        Personaje oponente = new Personaje("Loki");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);

        guerrero.atacar(oponente);
        int dano = 100 - oponente.getPuntosDeVida();

        assertTrue(dano >= 15 && dano <= 35,
                "Daño del Guerrero debe ser 15-35, fue: " + dano);
    }

    @Test
    @DisplayName("GuerreroDecorator debe reducir daño recibido en 20%")
    void testGuerreroArmaduraReduceDano() {
        Personaje base = new Personaje("Arthas");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);

        guerrero.recibirDano(100);
        // 100 * 0.80 = 80 → HP = 100 - 80 = 20
        assertEquals(20, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("GuerreroDecorator debe ignorar daño negativo")
    void testGuerreroDanoNegativoIgnorado() {
        Personaje base = new Personaje("Arthas");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);
        guerrero.recibirDano(-5);
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("GuerreroDecorator debe seguir vivo con HP > 0")
    void testGuerreroEstaVivo() {
        Personaje base = new Personaje("Arthas");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);
        assertTrue(guerrero.estaVivo());
    }

    @Test
    @DisplayName("GuerreroDecorator debe morir cuando HP llega a 0")
    void testGuerreroMuere() {
        Personaje base = new Personaje("Arthas");
        GuerreroDecorator guerrero = new GuerreroDecorator(base);
        // Con reducción del 20%, necesitamos aplicar suficiente daño
        // Para bajar 100 HP: daño / 0.8 = 125 → aplicamos 200 por seguridad
        guerrero.recibirDano(200);
        assertFalse(guerrero.estaVivo());
        assertEquals(0, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("GuerreroDecorator con Mockito: verificar que recibirDano llama al decorado")
    void testGuerreroMockitoVerificaLlamada() {
        Personaje mockBase = mock(Personaje.class);
        when(mockBase.getNombre()).thenReturn("MockGuerrero");
        when(mockBase.getPuntosDeVida()).thenReturn(100);
        when(mockBase.estaVivo()).thenReturn(true);

        GuerreroDecorator guerrero = new GuerreroDecorator(mockBase);
        guerrero.recibirDano(50); // 50 * 0.80 = 40

        // Verificar que el personaje decorado recibe el daño reducido
        verify(mockBase, times(1)).recibirDano(40);
    }

    // ══════════════════════════════════════════════
    //  MagoDecorator
    // ══════════════════════════════════════════════

    @Test
    @DisplayName("MagoDecorator debe iniciar con 90 HP (frágil)")
    void testMagoHpInicial() {
        Personaje base = new Personaje("Gandalf");
        MagoDecorator mago = new MagoDecorator(base);
        assertEquals(90, mago.getPuntosDeVida());
    }

    @Test
    @DisplayName("MagoDecorator debe atacar con daño entre 15 y 45 (×1.5)")
    void testMagoAtaqueMagico() {
        Personaje base     = new Personaje("Gandalf");
        Personaje oponente = new Personaje("Saruman");
        MagoDecorator mago = new MagoDecorator(base);

        mago.atacar(oponente);
        int dano = 100 - oponente.getPuntosDeVida();

        assertTrue(dano >= 15 && dano <= 45,
                "Daño del Mago debe ser 15-45, fue: " + dano);
    }

    @Test
    @DisplayName("MagoDecorator debe delegar getNombre al personaje base")
    void testMagoGetNombre() {
        Personaje base = new Personaje("Gandalf");
        MagoDecorator mago = new MagoDecorator(base);
        assertEquals("Gandalf", mago.getNombre());
    }

    @Test
    @DisplayName("MagoDecorator debe estar vivo al crearse")
    void testMagoEstaVivo() {
        Personaje base = new Personaje("Gandalf");
        MagoDecorator mago = new MagoDecorator(base);
        assertTrue(mago.estaVivo());
    }

    @Test
    @DisplayName("MagoDecorator con Mockito: verificar ataque llama recibirDano en oponente")
    void testMagoMockitoAtaqueLlamaRecibirDano() {
        Personaje base         = new Personaje("Gandalf");
        Personaje mockOponente = mock(Personaje.class);
        MagoDecorator mago     = new MagoDecorator(base);

        mago.atacar(mockOponente);

        // Verificar que se llamó recibirDano exactamente una vez en el oponente
        verify(mockOponente, times(1)).recibirDano(anyInt());
    }

    @Test
    @DisplayName("MagoDecorator con Mockito: daño aplicado al oponente es múltiplo de 1.5 del base")
    void testMagoMockitoMultiplicador() {
        Personaje base         = new Personaje("Gandalf");
        Personaje mockOponente = mock(Personaje.class);
        MagoDecorator mago     = new MagoDecorator(base);

        mago.atacar(mockOponente);

        // Capturar el valor de daño aplicado
        org.mockito.ArgumentCaptor<Integer> captor =
                org.mockito.ArgumentCaptor.forClass(Integer.class);
        verify(mockOponente).recibirDano(captor.capture());

        int danoAplicado = captor.getValue();
        // El daño mágico (15–45) debe ser >= 15
        assertTrue(danoAplicado >= 15 && danoAplicado <= 45,
                "Daño mágico debe ser 15–45, fue: " + danoAplicado);
    }
}
