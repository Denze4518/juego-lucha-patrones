package com.juego.patrones;

import com.juego.model.Personaje;
import com.juego.patrones.decorator.GuerreroDecorator;
import com.juego.patrones.decorator.MagoDecorator;
import com.juego.patrones.factory.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del patrón Factory Method.
 * Verifica que cada fábrica crea el tipo correcto de personaje.
 */
@DisplayName("FactoryTest - Pruebas del patrón Factory Method")
class FactoryTest {

    // ── PersonajeNormalFactory ────────────────────
    @Test
    @DisplayName("PersonajeNormalFactory debe crear un Personaje base")
    void testNormalFactoryCreaPersonajeBase() {
        PersonajeFactory factory = new PersonajeNormalFactory();
        Personaje p = factory.crearPersonaje("Link");
        assertNotNull(p);
        assertFalse(p instanceof GuerreroDecorator);
        assertFalse(p instanceof MagoDecorator);
    }

    @Test
    @DisplayName("PersonajeNormalFactory debe crear personaje con nombre correcto")
    void testNormalFactoryNombre() {
        PersonajeFactory factory = new PersonajeNormalFactory();
        Personaje p = factory.crearPersonaje("Link");
        assertEquals("Link", p.getNombre());
    }

    @Test
    @DisplayName("PersonajeNormalFactory debe crear personaje con 100 HP")
    void testNormalFactoryHP() {
        PersonajeFactory factory = new PersonajeNormalFactory();
        Personaje p = factory.crearPersonaje("Link");
        assertEquals(100, p.getPuntosDeVida());
    }

    // ── GuerreroFactory ───────────────────────────
    @Test
    @DisplayName("GuerreroFactory debe crear un GuerreroDecorator")
    void testGuerreroFactoryCreaDecorator() {
        PersonajeFactory factory = new GuerreroFactory();
        Personaje p = factory.crearPersonaje("Aragorn");
        assertInstanceOf(GuerreroDecorator.class, p);
    }

    @Test
    @DisplayName("GuerreroFactory debe crear personaje con nombre correcto")
    void testGuerreroFactoryNombre() {
        PersonajeFactory factory = new GuerreroFactory();
        Personaje p = factory.crearPersonaje("Aragorn");
        assertEquals("Aragorn", p.getNombre());
    }

    @Test
    @DisplayName("GuerreroFactory debe crear personaje con 100 HP")
    void testGuerreroFactoryHP() {
        PersonajeFactory factory = new GuerreroFactory();
        Personaje p = factory.crearPersonaje("Aragorn");
        assertEquals(100, p.getPuntosDeVida());
    }

    @Test
    @DisplayName("GuerreroFactory: personaje debe estar vivo al crearse")
    void testGuerreroFactoryVivo() {
        PersonajeFactory factory = new GuerreroFactory();
        Personaje p = factory.crearPersonaje("Aragorn");
        assertTrue(p.estaVivo());
    }

    // ── MagoFactory ───────────────────────────────
    @Test
    @DisplayName("MagoFactory debe crear un MagoDecorator")
    void testMagoFactoryCreaDecorator() {
        PersonajeFactory factory = new MagoFactory();
        Personaje p = factory.crearPersonaje("Merlin");
        assertInstanceOf(MagoDecorator.class, p);
    }

    @Test
    @DisplayName("MagoFactory debe crear personaje con nombre correcto")
    void testMagoFactoryNombre() {
        PersonajeFactory factory = new MagoFactory();
        Personaje p = factory.crearPersonaje("Merlin");
        assertEquals("Merlin", p.getNombre());
    }

    @Test
    @DisplayName("MagoFactory debe crear personaje con 90 HP (frágil)")
    void testMagoFactoryHP() {
        PersonajeFactory factory = new MagoFactory();
        Personaje p = factory.crearPersonaje("Merlin");
        assertEquals(90, p.getPuntosDeVida());
    }

    @Test
    @DisplayName("MagoFactory: personaje debe estar vivo al crearse")
    void testMagoFactoryVivo() {
        PersonajeFactory factory = new MagoFactory();
        Personaje p = factory.crearPersonaje("Merlin");
        assertTrue(p.estaVivo());
    }

    // ── Polimorfismo via interfaz ─────────────────
    @Test
    @DisplayName("Todas las fábricas deben producir personajes que sepan atacar")
    void testTodasLasFactoriasProducenPersonajesQueAtacan() {
        PersonajeFactory[] factories = {
            new PersonajeNormalFactory(),
            new GuerreroFactory(),
            new MagoFactory()
        };
        Personaje oponente = new Personaje("Objetivo");

        for (PersonajeFactory factory : factories) {
            Personaje p = factory.crearPersonaje("Atacante");
            int hpAntes = oponente.getPuntosDeVida();
            p.atacar(oponente);
            assertTrue(oponente.getPuntosDeVida() < hpAntes || !oponente.estaVivo(),
                    factory.getClass().getSimpleName() + " debe causar daño");
            // Resetear HP del oponente para siguiente iteración
            oponente = new Personaje("Objetivo");
        }
    }
}
