package com.juego.model;

import java.util.Random;

/**
 * Componente base del patrón Decorator.
 * Representa un personaje de combate con los métodos exigidos por el enunciado:
 *   - atacar(Personaje oponente)
 *   - estaVivo()
 *   - getNombre()
 *   - getPuntosDeVida()
 */
public class Personaje {

    private String nombre;
    private int puntosDeVida;
    private final int MAX_DANO = 30;
    private final int MIN_DANO = 10;
    private Random rand;

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.puntosDeVida = 100;
        this.rand = new Random();
    }

    /**
     * Ataca al oponente causando daño aleatorio entre 10 y 30.
     * @param oponente Personaje que recibe el daño.
     */
    public void atacar(Personaje oponente) {
        int dano = rand.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO;
        oponente.recibirDano(dano);
        System.out.println("   " + this.nombre + " ataca a " + oponente.getNombre()
                + " y causa " + dano + " puntos de daño.");
    }

    /**
     * Reduce los puntos de vida del personaje.
     * @param dano Cantidad de daño recibido (ignorado si es negativo).
     */
    public void recibirDano(int dano) {
        if (dano < 0) return;
        this.puntosDeVida -= dano;
        if (this.puntosDeVida < 0) {
            this.puntosDeVida = 0;
        }
    }

    /** @return true si el personaje tiene HP > 0. */
    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    /** @return Nombre del personaje. */
    public String getNombre() {
        return this.nombre;
    }

    /** @return Puntos de vida actuales. */
    public int getPuntosDeVida() {
        return this.puntosDeVida;
    }

    // ── Setter protegido para que los decorators puedan ajustar el HP ──
    protected void setPuntosDeVida(int puntosDeVida) {
        this.puntosDeVida = Math.max(0, puntosDeVida);
    }
}
