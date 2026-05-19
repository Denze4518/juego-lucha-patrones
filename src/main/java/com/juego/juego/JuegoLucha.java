package com.juego.juego;

import com.juego.model.Personaje;

import java.util.Scanner;

/**
 * Controla el flujo del juego por turnos.
 *
 * Ofrece dos modos:
 *   - iniciarPelea()              → automático (usado por pruebas unitarias)
 *   - iniciarPeleaInteractiva()   → el jugador presiona Enter en cada turno
 */
public class JuegoLucha {

    private static final int HP_MAXIMO = 100;
    private static final int ANCHO_BARRA_HP = 18;
    private static final String LINEA = "═".repeat(58);

    private Personaje jugador1;
    private Personaje jugador2;

    public JuegoLucha(Personaje jugador1, Personaje jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    // ══════════════════════════════════════════════
    //  MODO AUTOMÁTICO (para pruebas unitarias)
    // ══════════════════════════════════════════════

    /**
     * Inicia la pelea automáticamente sin interacción del usuario.
     * Requerido por el enunciado del taller.
     */
    public void iniciarPelea() {
        System.out.println("\n=== LA PELEA COMIENZA ===");
        System.out.println(jugador1.getNombre() + " vs " + jugador2.getNombre());
        System.out.println("=========================");

        int turno = 1;
        while (jugador1.estaVivo() && jugador2.estaVivo()) {
            System.out.println("\n-- Turno " + turno + " --");
            printEstado();

            jugador1.atacar(jugador2);
            if (jugador2.estaVivo()) {
                jugador2.atacar(jugador1);
            }
            turno++;
        }

        anunciarGanador();
    }

    // ══════════════════════════════════════════════
    //  MODO INTERACTIVO (para Main.java)
    // ══════════════════════════════════════════════

    /**
     * Inicia la pelea turno a turno: el jugador activo presiona Enter para atacar.
     * @param scanner Scanner compartido desde Main.
     */
    public void iniciarPeleaInteractiva(Scanner scanner) {
        System.out.println("\n╔" + LINEA + "╗");
        imprimirFila("¡LA BATALLA COMIENZA!");
        System.out.println("╚" + LINEA + "╝");

        int turno = 1;

        while (jugador1.estaVivo() && jugador2.estaVivo()) {

            System.out.println("\n┌" + "─".repeat(58) + "┐");
            System.out.printf("│  TURNO %-50d│%n", turno);
            System.out.println("├" + "─".repeat(58) + "┤");
            printEstadoFormateado();
            System.out.println("└" + "─".repeat(58) + "┘");

            // ── Turno de Jugador 1 ──
            anunciarTurno(jugador1);
            scanner.nextLine();

            jugador1.atacar(jugador2);
            printHpTrasAtaque(jugador2);

            if (!jugador2.estaVivo()) break;

            // ── Turno de Jugador 2 ──
            anunciarTurno(jugador2);
            scanner.nextLine();

            jugador2.atacar(jugador1);
            printHpTrasAtaque(jugador1);

            turno++;
            System.out.println();
        }

        anunciarGanadorInteractivo();
    }

    // ══════════════════════════════════════════════
    //  HELPERS PRIVADOS
    // ══════════════════════════════════════════════

    private void printEstado() {
        System.out.println(jugador1.getNombre() + " HP: " + jugador1.getPuntosDeVida()
                + " | " + jugador2.getNombre() + " HP: " + jugador2.getPuntosDeVida());
    }

    private void printEstadoFormateado() {
        System.out.printf("│  %-16s HP %3d  %s  %3d%%     │%n",
                jugador1.getNombre(),
                jugador1.getPuntosDeVida(),
                barraHP(jugador1.getPuntosDeVida(), HP_MAXIMO),
                porcentajeHP(jugador1.getPuntosDeVida(), HP_MAXIMO));
        System.out.printf("│  %-16s HP %3d  %s  %3d%%     │%n",
                jugador2.getNombre(),
                jugador2.getPuntosDeVida(),
                barraHP(jugador2.getPuntosDeVida(), HP_MAXIMO),
                porcentajeHP(jugador2.getPuntosDeVida(), HP_MAXIMO));
    }

    private void printHpTrasAtaque(Personaje personaje) {
        System.out.println("   → " + personaje.getNombre()
                + " queda con " + personaje.getPuntosDeVida() + " HP  "
                + barraHP(personaje.getPuntosDeVida(), HP_MAXIMO));
        if (!personaje.estaVivo()) {
            System.out.println("   💀 " + personaje.getNombre() + " ha caído.");
        }
    }

    private void anunciarTurno(Personaje jugador) {
        System.out.println("\n⚔  Turno de " + jugador.getNombre());
        System.out.print("   Presiona ENTER para atacar... ");
    }

    /** Genera una barra visual de HP: ████░░░░ */
    private String barraHP(int hp, int maxHP) {
        int total   = ANCHO_BARRA_HP;
        int llenos  = (int) Math.round((double) hp / maxHP * total);
        llenos      = Math.max(0, Math.min(llenos, total));
        return "[" + "█".repeat(llenos) + "░".repeat(total - llenos) + "]";
    }

    private int porcentajeHP(int hp, int maxHP) {
        int porcentaje = (int) Math.round((double) hp / maxHP * 100);
        return Math.max(0, Math.min(porcentaje, 100));
    }

    private void anunciarGanador() {
        System.out.println("\n=== FIN DE LA PELEA ===");
        Personaje ganador = obtenerGanador();
        System.out.println("¡" + ganador.getNombre() + " ha ganado!");
        System.out.println("HP restante: " + ganador.getPuntosDeVida());
    }

    private void anunciarGanadorInteractivo() {
        Personaje ganador = obtenerGanador();
        System.out.println("\n╔" + LINEA + "╗");
        imprimirFila("FIN DE LA PELEA");
        System.out.println("╠" + LINEA + "╣");
        System.out.printf( "║  👑 Ganador: %-43s║%n", ganador.getNombre());
        System.out.printf( "║  HP restante: %-45d║%n", ganador.getPuntosDeVida());
        System.out.println("╚" + LINEA + "╝\n");
    }

    private void imprimirFila(String texto) {
        System.out.printf("║ %-56s ║%n", texto);
    }

    /** @return El personaje ganador (útil para pruebas unitarias). */
    public Personaje obtenerGanador() {
        return jugador1.estaVivo() ? jugador1 : jugador2;
    }

    public Personaje getJugador1() { return jugador1; }
    public Personaje getJugador2() { return jugador2; }
}
