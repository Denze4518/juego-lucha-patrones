package com.juego.patrones.decorator;

import com.juego.model.Personaje;

/**
 * PATRÓN ESTRUCTURAL: Decorator (base abstracto)
 *
 * Envuelve un Personaje y delega todos los métodos al componente interno.
 * Las subclases concretas sobreescriben solo el comportamiento que modifican,
 * siguiendo el principio Open/Closed.
 */
public abstract class PersonajeDecorator extends Personaje {

    /** Componente envuelto por el decorator. */
    protected Personaje personajeDecorado;

    public PersonajeDecorator(Personaje personaje) {
        super(personaje.getNombre());
        this.personajeDecorado = personaje;
    }

    @Override
    public void atacar(Personaje oponente) {
        personajeDecorado.atacar(oponente);
    }

    @Override
    public void recibirDano(int dano) {
        personajeDecorado.recibirDano(dano);
    }

    @Override
    public boolean estaVivo() {
        return personajeDecorado.estaVivo();
    }

    @Override
    public String getNombre() {
        return personajeDecorado.getNombre();
    }

    @Override
    public int getPuntosDeVida() {
        return personajeDecorado.getPuntosDeVida();
    }
}
