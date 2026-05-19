package com.juego.patrones.factory;

import com.juego.model.Personaje;

/**
 * PATRÓN CREACIONAL: Factory Method — PersonajeNormalFactory
 *
 * Crea un Personaje base sin ningún decorator aplicado.
 * Daño: 10–30 · HP: 100
 */
public class PersonajeNormalFactory implements PersonajeFactory {

    @Override
    public Personaje crearPersonaje(String nombre) {
        return new Personaje(nombre);
    }
}
