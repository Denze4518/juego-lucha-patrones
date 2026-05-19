package com.juego.patrones.factory;

import com.juego.model.Personaje;
import com.juego.patrones.decorator.MagoDecorator;

/**
 * PATRÓN CREACIONAL: Factory Method — MagoFactory
 *
 * Crea un Personaje envuelto con MagoDecorator.
 * Daño: 15–45 (×1.5 mágico) · HP: 90 (frágil)
 */
public class MagoFactory implements PersonajeFactory {

    @Override
    public Personaje crearPersonaje(String nombre) {
        Personaje base = new Personaje(nombre);
        return new MagoDecorator(base);
    }
}
