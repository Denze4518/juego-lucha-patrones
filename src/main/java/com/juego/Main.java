package com.juego;

import com.juego.juego.JuegoLucha;
import com.juego.model.Personaje;
import com.juego.patrones.factory.GuerreroFactory;
import com.juego.patrones.factory.MagoFactory;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.factory.PersonajeNormalFactory;

import java.util.Scanner;

/**
 * Punto de entrada del juego.
 * Permite al usuario configurar los personajes e interactuar turno a turno.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String LINEA = "═".repeat(48);

    public static void main(String[] args) {
        printBanner();

        System.out.println("Prepara a tus combatientes antes de entrar a la arena.\n");

        Personaje jugador1 = configurarJugador(1);
        Personaje jugador2 = configurarJugador(2);

        mostrarVersus(jugador1, jugador2);
        presionarEnter("Presiona ENTER para iniciar la batalla...");

        JuegoLucha juego = new JuegoLucha(jugador1, jugador2);
        juego.iniciarPeleaInteractiva(scanner);

        System.out.println("\n¿Deseas jugar de nuevo? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        if (respuesta.equals("s")) {
            main(args);
        } else {
            System.out.println("\n¡Hasta la próxima batalla! ⚔\n");
        }

        scanner.close();
    }

    // ── Configurar jugador ────────────────────────
    private static Personaje configurarJugador(int numero) {
        System.out.println("╔" + LINEA + "╗");
        imprimirFila("JUGADOR " + numero);
        System.out.println("╚" + LINEA + "╝");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Jugador" + numero;

        System.out.println("\nElige tu clase:");
        System.out.println("  1) Normal   | HP 100 | Daño 10-30 | Balanceado");
        System.out.println("  2) Guerrero | HP 100 | Daño 15-35 | Armadura -20%");
        System.out.println("  3) Mago     | HP  90 | Daño 15-45 | Magia x1.5");
        System.out.print("Opción (1-3): ");

        int opcion = leerOpcion(1, 3);
        PersonajeFactory factory = switch (opcion) {
            case 2 -> new GuerreroFactory();
            case 3 -> new MagoFactory();
            default -> new PersonajeNormalFactory();
        };

        Personaje p = factory.crearPersonaje(nombre);
        System.out.println("\n✔ " + nombre + " entra como " + getClase(p)
                + " con " + p.getPuntosDeVida() + " HP.\n");
        return p;
    }

    // ── Helpers ───────────────────────────────────
    private static int leerOpcion(int min, int max) {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) return valor;
                System.out.print("Por favor elige entre " + min + " y " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un número válido (" + min + "-" + max + "): ");
            }
        }
    }

    private static void presionarEnter(String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine();
    }

    private static String getClase(Personaje p) {
        String nombre = p.getClass().getSimpleName();
        return switch (nombre) {
            case "GuerreroDecorator" -> "Guerrero";
            case "MagoDecorator"     -> "Mago";
            default                  -> "Normal";
        };
    }

    private static void mostrarVersus(Personaje jugador1, Personaje jugador2) {
        System.out.println("\n╔" + LINEA + "╗");
        imprimirFila("ARENA LISTA");
        System.out.println("╠" + LINEA + "╣");
        imprimirFila(jugador1.getNombre() + " [" + getClase(jugador1) + "]");
        imprimirFila("VS");
        imprimirFila(jugador2.getNombre() + " [" + getClase(jugador2) + "]");
        System.out.println("╚" + LINEA + "╝");
    }

    private static void imprimirFila(String texto) {
        System.out.printf("║ %-46s ║%n", texto);
    }

    private static void printBanner() {
        System.out.println();
        System.out.println("╔" + LINEA + "╗");
        imprimirFila("⚔ JUEGO DE LUCHA ⚔");
        imprimirFila("Factory Method + Decorator");
        imprimirFila("Combate por turnos en consola");
        System.out.println("╚" + LINEA + "╝");
        System.out.println();
    }
}
