package com.juego.patrones.decorator;

import com.juego.model.Personaje;

import java.util.Random;

/**
 * PATRÓN ESTRUCTURAL: Decorator concreto — GuerreroDecorator
 *
 * Agrega responsabilidades al Personaje base dinámicamente:
 *   - Armadura: reduce el daño recibido en un 20%.
 *   - Espada:   incrementa el daño de ataque en +5 puntos.
 */
public class GuerreroDecorator extends PersonajeDecorator {

    private static final int BONUS_ESPADA  = 5;
    private static final double REDUCCION_ARMADURA = 0.20;

    private final int MAX_DANO = 30;
    private final int MIN_DANO = 10;
    private final Random rand  = new Random();

    public GuerreroDecorator(Personaje personaje) {
        super(personaje);
    }

    /**
     * Sobreescribe atacar(): aplica daño base + BONUS_ESPADA.
     */
    @Override
    public void atacar(Personaje oponente) {
        int dano = rand.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO + BONUS_ESPADA;
        oponente.recibirDano(dano);
        System.out.println("   " + getNombre() + " carga con espada contra " + oponente.getNombre()
                + " y causa " + dano + " puntos de daño.");
    }

    /**
     * Sobreescribe recibirDano(): la armadura absorbe el 20% del daño entrante.
     */
    @Override
    public void recibirDano(int dano) {
        if (dano < 0) return;
        int danoReducido = (int) Math.ceil(dano * (1 - REDUCCION_ARMADURA));
        System.out.println("   " + getNombre() + " bloquea con armadura: " + dano
                + " -> " + danoReducido + " de daño efectivo.");
        personajeDecorado.recibirDano(danoReducido);
    }

    public int getBonusEspada() {
        return BONUS_ESPADA;
    }

    public double getReduccionArmadura() {
        return REDUCCION_ARMADURA;
    }
}
