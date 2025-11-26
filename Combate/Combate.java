package Combate;


import Items.*;
import Pokemon.*;
import Pokemon.Charmander;
import Pokemon.Pokemon;
import Pokemon.Squirtle;

import java.util.Scanner;

public class Combate {

    public static void main(String[] args) {
        System.out.println("Bienvenido al mundo Pokémon");
        //Equipo Pokemon
        Pokemon[] equipo = new Pokemon[2];
        equipo[0] = new Charmander();
        equipo[1] = new Squirtle();
        //Equipo Enemigo
        Pokemon[] equipoEnemigo = { new Charmander(), new Squirtle() };
        //Mochila
        Curas[] mochila = new Curas[3];{
            mochila[0] = new Pociones();
            mochila[1] = new Superpociones();
            mochila[2] = new Ultrapociones();
        }
        combatePokemon(equipo,equipoEnemigo,mochila);
    }


    public static boolean cambiarPokemon(Pokemon[] equipo, int turnoActual) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Elige Pokémon para cambiar (C para cancelar):");

        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i].vida > 0 && i != turnoActual) {
                System.out.println((i + 1) + ") " + equipo[i].nombre + " | " + barraVida(equipo[i].vida, equipo[i].vidamax));
            }
        }

        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("C")) {
            System.out.println("Cancelado.");
            return false; // No se gasta el turno
        }

        int nuevo;
        try {
            nuevo = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return false;
        }

        if (nuevo >= 0 && nuevo < equipo.length && equipo[nuevo].vida > 0 && nuevo != turnoActual) {
            System.out.println("¡Cambiando a " + equipo[nuevo].nombre + "!");
            // Se hace el cambio devolviendo el nuevo índice
            int temp = equipo[nuevo].vida; // si quieres almacenar algo temporal
            equipo[nuevo] = equipo[turnoActual];
            equipo[turnoActual] = equipo[nuevo];
            return true; // acción realizada
        } else {
            System.out.println("Opción inválida.");
            return false; // no se gasta turno
        }
    }


    public static boolean accederMochila(Pokemon[] equipo, Curas[] mochila) {
        Scanner sc = new Scanner(System.in);
        boolean usado = false;

        while (!usado) {
            System.out.println("Elige un objeto (C para cancelar):");
            for (int i = 0; i < mochila.length; i++) {
                System.out.println((i+1) + ") " + mochila[i].cantidad + "x " + mochila[i].nombre);
            }

            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("C")) {
                System.out.println("Cancelado.");
                return false; // cancelar no consume turno
            }

            int opcion;
            try {
                opcion = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            if (opcion < 0 || opcion >= mochila.length) continue;
            if (mochila[opcion].cantidad <= 0) {
                System.out.println("No tienes " + mochila[opcion].nombre);
                continue;
            }

            System.out.println("¿Qué Pokémon quieres curar? (C para cancelar)");
            for (int i = 0; i < equipo.length; i++) {
                if (equipo[i].vida > 0) {
                    System.out.println((i+1) + ") " + equipo[i].nombre + " | " + barraVida(equipo[i].vida, equipo[i].vidamax));
                }
            }

            String inputPokemon = sc.nextLine().trim();
            if (inputPokemon.equalsIgnoreCase("C")) {
                System.out.println("Cancelado.");
                return false; // cancelar no consume turno
            }

            int opcionPokemon;
            try {
                opcionPokemon = Integer.parseInt(inputPokemon) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            if (opcionPokemon < 0 || opcionPokemon >= equipo.length || equipo[opcionPokemon].vida <= 0) {
                System.out.println("Opción inválida.");
                continue;
            }

            consumirPocion(equipo[opcionPokemon], mochila[opcion]);
            usado = true;
        }

        return true; // acción realizada, turno consumido
    }



    public static void consumirPocion(Pokemon p, Curas pocion) {
        p.vida += pocion.vida;
        if (p.vida > p.vidamax) p.vida = p.vidamax;
        pocion.cantidad--;

        System.out.println(p.nombre + " se ha curado correctamente");
        System.out.println(p.nombre + " : " + barraVida(p.vida, p.vidamax));
        System.out.println("Te quedan: " + pocion.cantidad + " " + pocion.nombre);
    }

    public static String barraVida(int vida, int vidamax) {
        int totalBloques = 20;
        int verdes = (vida * totalBloques) / vidamax;
        int blancos = totalBloques - verdes;

        String GREEN = "\u001B[38;5;46m";
        String WHITE = "\u001B[37m";
        String RESET = "\u001B[0m";

        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < verdes; i++) barra.append(GREEN + "#" + RESET);
        for (int i = 0; i < blancos; i++) barra.append(WHITE + "#" + RESET);
        barra.append("] ").append(vida).append("/").append(vidamax).append(" ps");

        return barra.toString();
    }
    public static void mostrarCombate(Pokemon jugador, Pokemon enemigo) {
        String GREEN = "\u001B[38;5;46m"; // verde
        String WHITE = "\u001B[37m";      // vida perdida
        String RESET = "\u001B[0m";

        int totalBloques = 20;

        // --- Barra jugador ---
        int verdesJugador = (jugador.vida * totalBloques) / jugador.vidamax;
        int blancosJugador = totalBloques - verdesJugador;
        StringBuilder barraJugador = new StringBuilder("[");
        for (int i = 0; i < verdesJugador; i++) barraJugador.append(GREEN + "#" + RESET);
        for (int i = 0; i < blancosJugador; i++) barraJugador.append(WHITE + "#" + RESET);
        barraJugador.append("] ");

        // --- Barra enemigo ---
        int verdesEnemigo = (enemigo.vida * totalBloques) / enemigo.vidamax;
        int blancosEnemigo = totalBloques - verdesEnemigo;
        StringBuilder barraEnemigo = new StringBuilder("[");
        for (int i = 0; i < verdesEnemigo; i++) barraEnemigo.append(GREEN + "#" + RESET);
        for (int i = 0; i < blancosEnemigo; i++) barraEnemigo.append(WHITE + "#" + RESET);
        barraEnemigo.append("] ");

        // --- Imprimir ---
        System.out.println(
                jugador.nombre + " " + barraJugador + jugador.vida + "/" + jugador.vidamax + " ps"
                        + "          VS          "
                        + enemigo.nombre + " " + barraEnemigo + enemigo.vida + "/" + enemigo.vidamax + " ps"
        );
    }
    public static void combatePokemon(Pokemon[] equipoJugador, Pokemon[] equipoEnemigo, Curas[] mochila) {
        Scanner sc = new Scanner(System.in);
        int turnoJugador = 0;
        int turnoEnemigo = 0;

        while (true) {
            // Buscar primer Pokémon vivo
            while (turnoJugador < equipoJugador.length && equipoJugador[turnoJugador].vida <= 0) turnoJugador++;
            while (turnoEnemigo < equipoEnemigo.length && equipoEnemigo[turnoEnemigo].vida <= 0) turnoEnemigo++;

            // Comprobar fin de combate
            if (turnoJugador >= equipoJugador.length) {
                System.out.println("¡Has perdido el combate!");
                break;
            }
            if (turnoEnemigo >= equipoEnemigo.length) {
                System.out.println("¡Has ganado el combate!");
                // Dar XP a todos los Pokémon vivos del equipo
                for (Pokemon p : equipoJugador) {
                    if (p.vida > 0) {
                        int xpGanada = p.calcularXP(equipoEnemigo[turnoEnemigo - 1]);
                        p.ganarXP(equipoEnemigo[turnoEnemigo - 1]);
                        p.subirNivel(xpGanada);
                    }
                }
                break;
            }

            Pokemon jugador = equipoJugador[turnoJugador];
            Pokemon enemigo = equipoEnemigo[turnoEnemigo];

            mostrarCombate(jugador, enemigo);

            // --- TURNO DEL JUGADOR ---
            boolean accionValida = false;
            while (!accionValida) {
                System.out.println("\n┌─────────────────────────┐");
                System.out.println("│ 1) Atacar               │");
                System.out.println("│ 2) Usar poción          │");
                System.out.println("│ 3) Cambiar Pokémon      │");
                System.out.println("└─────────────────────────┘");
                System.out.print("Elige tu acción (C para cancelar): ");
                String opcionInput = sc.nextLine().trim();

                if (opcionInput.equalsIgnoreCase("C")) {
                    System.out.println("Cancelado. Elige otra acción.");
                    continue; // No se gasta el turno
                }

                int opcion;
                try {
                    opcion = Integer.parseInt(opcionInput);
                } catch (NumberFormatException e) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                switch (opcion) {
                    case 1 -> { // ATACAR
                        System.out.println("Elige movimiento (C para cancelar):");
                        for (int i = 0; i < jugador.movimientos.length; i++) {
                            if (jugador.movimientos[i] != null)
                                System.out.println((i + 1) + ") " + jugador.movimientos[i].nombre_atk);
                        }
                        String movInput = sc.nextLine().trim();
                        if (movInput.equalsIgnoreCase("C")) {
                            System.out.println("Cancelado.");
                            continue;
                        }
                        int mov;
                        try {
                            mov = Integer.parseInt(movInput) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Movimiento inválido.");
                            continue;
                        }
                        if (mov >= 0 && mov < jugador.movimientos.length && jugador.movimientos[mov] != null) {
                            jugador.usarMovimiento(mov, enemigo);
                            accionValida = true;
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                    }

                    case 2 -> { // USAR POCIÓN
                        if (accederMochila(equipoJugador, mochila)) {
                            accionValida = true;
                        } else {
                            System.out.println("Cancelado.");
                        }
                    }

                    case 3 -> { // CAMBIAR POKÉMON
                        if (cambiarPokemon(equipoJugador, turnoJugador)) {
                            accionValida = true;
                        } else {
                            System.out.println("Cancelado.");
                        }
                    }

                    default -> System.out.println("Opción inválida.");
                }
            }

            // --- TURNO DEL ENEMIGO ---
            enemigo = equipoEnemigo[turnoEnemigo];
            jugador = equipoJugador[turnoJugador];

            if (enemigo.vida > 0) {
                try { Thread.sleep(700); } catch (InterruptedException ignored) {}

                // Elegir movimiento aleatorio
                int mov;
                do {
                    mov = (int)(Math.random() * 4);
                } while (enemigo.movimientos[mov] == null);

                System.out.println(enemigo.nombre + " usó " + enemigo.movimientos[mov].nombre_atk + "!");
                try { Thread.sleep(600); } catch (InterruptedException ignored) {}
                enemigo.usarMovimiento(mov, jugador);

                // Si el Pokémon del jugador cae, dar XP
                if (jugador.vida <= 0) {
                    int xpGanada = jugador.calcularXP(enemigo);
                    jugador.ganarXP(enemigo);
                    jugador.subirNivel(xpGanada);
                }
            }

            // Si el enemigo cae, dar XP al jugador
            if (enemigo.vida <= 0) {
                int xpGanada = jugador.calcularXP(enemigo);
                jugador.ganarXP(enemigo);
                jugador.subirNivel(xpGanada);
                System.out.println(enemigo.nombre + " se ha debilitado.");
                turnoEnemigo++;
            }

            System.out.println("\n--- Fin del turno ---\n");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }

}



