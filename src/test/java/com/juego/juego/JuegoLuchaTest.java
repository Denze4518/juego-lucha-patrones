package com.juego.juego;

import com.juego.model.Personaje;
import com.juego.juego.JuegoLucha;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de JuegoLucha.
 * Usa Mockito para controlar el comportamiento de los personajes
 * y verificar el flujo del juego sin depender del azar.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("JuegoLuchaTest - Pruebas del flujo del juego")
class JuegoLuchaTest {

    // ── Construcción ──────────────────────────────
    @Test
    @DisplayName("JuegoLucha debe inicializarse con los dos jugadores")
    void testInicializacion() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");
        JuegoLucha juego = new JuegoLucha(p1, p2);

        assertEquals("Thor", juego.getJugador1().getNombre());
        assertEquals("Loki", juego.getJugador2().getNombre());
    }

    // ── Ganador con Mockito ───────────────────────

    @Test
    @DisplayName("Jugador1 gana cuando Jugador2 pierde todo el HP")
    void testJugador1Gana() {
        // Mockito: jugador1 siempre mata al oponente en el primer ataque
        Personaje mockP1 = mock(Personaje.class);
        Personaje mockP2 = mock(Personaje.class);

        when(mockP1.getNombre()).thenReturn("Heroe");
        when(mockP2.getNombre()).thenReturn("Villano");
        when(mockP1.estaVivo()).thenReturn(true);

        // Simula que después del primer ataque de p1, p2 queda muerto
        when(mockP2.estaVivo())
                .thenReturn(true)   // antes del ataque de p1
                .thenReturn(false); // después del ataque de p1

        JuegoLucha juego = new JuegoLucha(mockP1, mockP2);
        juego.iniciarPelea();

        Personaje ganador = juego.obtenerGanador();
        assertEquals("Heroe", ganador.getNombre());
    }

    @Test
    @DisplayName("Jugador2 gana cuando Jugador1 pierde todo el HP")
    void testJugador2Gana() {
        Personaje mockP1 = mock(Personaje.class);
        Personaje mockP2 = mock(Personaje.class);

        when(mockP1.getNombre()).thenReturn("Heroe");
        when(mockP2.getNombre()).thenReturn("Villano");

        // p1 muere en el primer turno tras recibir el ataque de p2
        when(mockP1.estaVivo())
                .thenReturn(true)    // comienza vivo
                .thenReturn(false);  // muere tras ataque de p2
        when(mockP2.estaVivo()).thenReturn(true); // p2 siempre vivo

        JuegoLucha juego = new JuegoLucha(mockP1, mockP2);
        juego.iniciarPelea();

        Personaje ganador = juego.obtenerGanador();
        assertEquals("Villano", ganador.getNombre());
    }

    @Test
    @DisplayName("iniciarPelea debe llamar atacar al menos una vez")
    void testIniciarPeleaLlamaAtacar() {
        Personaje mockP1 = mock(Personaje.class);
        Personaje mockP2 = mock(Personaje.class);

        when(mockP1.getNombre()).thenReturn("P1");
        when(mockP2.getNombre()).thenReturn("P2");
        when(mockP1.estaVivo()).thenReturn(true);
        when(mockP2.estaVivo())
                .thenReturn(true)
                .thenReturn(false);

        JuegoLucha juego = new JuegoLucha(mockP1, mockP2);
        juego.iniciarPelea();

        verify(mockP1, atLeastOnce()).atacar(mockP2);
    }

    @Test
    @DisplayName("Jugador2 no ataca si ya está muerto tras el turno de Jugador1")
    void testJugador2NoAtacaSiEstaMuerto() {
        Personaje mockP1 = mock(Personaje.class);
        Personaje mockP2 = mock(Personaje.class);

        when(mockP1.getNombre()).thenReturn("P1");
        when(mockP2.getNombre()).thenReturn("P2");
        when(mockP1.estaVivo()).thenReturn(true);
        when(mockP2.estaVivo())
                .thenReturn(true)   // condición del while
                .thenReturn(false); // verificación tras ataque de p1

        JuegoLucha juego = new JuegoLucha(mockP1, mockP2);
        juego.iniciarPelea();

        // p2 nunca debe haber atacado porque ya estaba muerto
        verify(mockP2, never()).atacar(any());
    }

    // ── Flujo real sin Mockito ────────────────────
    @Test
    @DisplayName("Pelea real: debe haber exactamente un ganador")
    void testPeleaRealUnGanador() {
        Personaje p1 = new Personaje("Guerrero");
        Personaje p2 = new Personaje("Dragon");
        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.iniciarPelea();

        // Exactamente uno debe estar vivo
        boolean unGanador = p1.estaVivo() ^ p2.estaVivo();
        assertTrue(unGanador, "Debe haber exactamente un ganador");
    }

    @Test
    @DisplayName("obtenerGanador retorna el personaje correcto")
    void testObtenerGanador() {
        Personaje p1 = new Personaje("Guerrero");
        Personaje p2 = new Personaje("Dragon");
        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.iniciarPelea();

        Personaje ganador = juego.obtenerGanador();
        assertTrue(ganador.estaVivo());
        assertTrue(
            ganador.getNombre().equals("Guerrero") ||
            ganador.getNombre().equals("Dragon")
        );
    }
}
