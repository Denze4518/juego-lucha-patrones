package com.juego.patrones.factory;

import com.juego.model.Personaje;
import com.juego.patrones.decorator.GuerreroDecorator;

/**
 * PATRÓN CREACIONAL: Factory Method — GuerreroFactory
 *
 * Crea un Personaje envuelto con GuerreroDecorator.
 * Daño: 15–35 (+5 espada) · HP: 100 · Armadura: -20% daño recibido
 */
public class GuerreroFactory implements PersonajeFactory {

    @Override
    public Personaje crearPersonaje(String nombre) {
        Personaje base = new Personaje(nombre);
        return new GuerreroDecorator(base);
    }
}
