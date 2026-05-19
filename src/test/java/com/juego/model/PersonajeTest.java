package com.juego.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de la clase Personaje.
 * Cubre: creación, recibirDano, estaVivo, getNombre, getPuntosDeVida, atacar.
 */
@DisplayName("PersonajeTest - Pruebas del componente base")
class PersonajeTest {

    private Personaje guerrero;

    @BeforeEach
    void setUp() {
        guerrero = new Personaje("Thor");
    }

    // ── Creación ─────────────────────────────────
    @Test
    @DisplayName("Debe crear personaje con nombre correcto")
    void testCreacionNombre() {
        assertEquals("Thor", guerrero.getNombre());
    }

    @Test
    @DisplayName("Debe crear personaje con 100 HP")
    void testCreacionHP() {
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Personaje nuevo debe estar vivo")
    void testCreacionEstaVivo() {
        assertTrue(guerrero.estaVivo());
    }

    // ── recibirDano ───────────────────────────────
    @Test
    @DisplayName("Debe reducir HP al recibir daño")
    void testRecibirDano() {
        guerrero.recibirDano(30);
        assertEquals(70, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("HP no debe ser negativo al recibir daño excesivo")
    void testHpNoNegativo() {
        guerrero.recibirDano(150);
        assertEquals(0, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Daño negativo no debe modificar el HP")
    void testDanoNegativoIgnorado() {
        guerrero.recibirDano(-10);
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Daño de 0 no debe modificar el HP")
    void testDanoCero() {
        guerrero.recibirDano(0);
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Recibir exactamente 100 de daño deja HP en 0")
    void testDanoExactoHP() {
        guerrero.recibirDano(100);
        assertEquals(0, guerrero.getPuntosDeVida());
        assertFalse(guerrero.estaVivo());
    }

    // ── estaVivo ──────────────────────────────────
    @Test
    @DisplayName("Personaje con HP > 0 debe estar vivo")
    void testEstaVivoConHP() {
        guerrero.recibirDano(50);
        assertTrue(guerrero.estaVivo());
    }

    @Test
    @DisplayName("Personaje con HP = 0 no debe estar vivo")
    void testNoEstaVivoSinHP() {
        guerrero.recibirDano(100);
        assertFalse(guerrero.estaVivo());
    }

    // ── atacar ────────────────────────────────────
    @Test
    @DisplayName("Ataque debe causar daño entre 10 y 30")
    void testRangoAtaque() {
        Personaje oponente = new Personaje("Loki");
        int vidaInicial = oponente.getPuntosDeVida();
        guerrero.atacar(oponente);
        int dano = vidaInicial - oponente.getPuntosDeVida();
        assertTrue(dano >= 10 && dano <= 30,
                "El daño debe estar entre 10 y 30, fue: " + dano);
    }

    @Test
    @DisplayName("Ataque debe reducir el HP del oponente")
    void testAtaqueReduceHpOponente() {
        Personaje oponente = new Personaje("Loki");
        guerrero.atacar(oponente);
        assertTrue(oponente.getPuntosDeVida() < 100);
    }

    @Test
    @DisplayName("Ataque repetido eventualmente derrota al oponente")
    void testAtaquesRepetikosDerrotanOponente() {
        Personaje oponente = new Personaje("Loki");
        // Con daño mínimo de 10, en 10 ataques se garantiza la derrota
        for (int i = 0; i < 10; i++) {
            guerrero.atacar(oponente);
        }
        assertFalse(oponente.estaVivo());
    }
}
