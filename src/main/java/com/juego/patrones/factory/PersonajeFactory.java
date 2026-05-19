package com.juego.patrones.factory;

import com.juego.model.Personaje;

/**
 * PATRÓN CREACIONAL: Factory Method
 *
 * Define la interfaz para crear objetos Personaje.
 * Cada subclase concreta de fábrica decide qué tipo de Personaje instanciar,
 * desacoplando la lógica de creación del resto del código.
 */
public interface PersonajeFactory {

    /**
     * Crea y retorna un Personaje según la implementación concreta.
     * @param nombre Nombre del personaje a crear.
     * @return Instancia de Personaje (puede ser decorada).
     */
    Personaje crearPersonaje(String nombre);
}
