package com.juego.patrones.decorator;

import com.juego.model.Personaje;

import java.util.Random;

/**
 * PATRÓN ESTRUCTURAL: Decorator concreto — MagoDecorator
 *
 * Agrega responsabilidades al Personaje base dinámicamente:
 *   - Magia: multiplica el daño de ataque por 1.5.
 *   - Fragilidad: el HP máximo se reduce a 90 al equipar.
 */
public class MagoDecorator extends PersonajeDecorator {

    private static final double MULTIPLICADOR_MAGICO = 1.5;
    private static final int    HP_INICIAL_MAGO      = 90;

    private final int MAX_DANO = 30;
    private final int MIN_DANO = 10;
    private final Random rand  = new Random();

    public MagoDecorator(Personaje personaje) {
        super(personaje);
        // Penalización de fragilidad: el mago empieza con 90 HP
        personajeDecorado.recibirDano(100 - HP_INICIAL_MAGO);
    }

    /**
     * Sobreescribe atacar(): aplica daño mágico amplificado × 1.5.
     */
    @Override
    public void atacar(Personaje oponente) {
        int danoBase   = rand.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO;
        int danoMagico = (int) (danoBase * MULTIPLICADOR_MAGICO);
        oponente.recibirDano(danoMagico);
        System.out.println("   " + getNombre() + " lanza un hechizo contra " + oponente.getNombre()
                + " y causa " + danoMagico + " puntos de daño mágico.");
    }

    public double getMultiplicadorMagico() {
        return MULTIPLICADOR_MAGICO;
    }

    public int getHpInicialMago() {
        return HP_INICIAL_MAGO;
    }
}
