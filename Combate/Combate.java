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
        equipo[0].subirNivel();
        combatePokemon(equipo,equipoEnemigo,mochila);
    }

    public static void elegirPokemon(Pokemon[] equipo) {
        Scanner sc = new Scanner(System.in);
        boolean elegido = false;

        while (!elegido) {
            System.out.println("Elige tu Pokémon:");
            for (int i = 0; i < equipo.length; i++) {
                System.out.println((i+1) + ". " + equipo[i].nombre + " | " + equipo[i].tipo + " | Vida: " + barraVida(equipo[i].vida, equipo[i].vidamax));
            }

            int opcion = sc.nextInt() - 1;
            if (opcion < 0 || opcion >= equipo.length) continue;
            if (equipo[opcion].vida <= 0) {
                System.out.println("No puedes elegir a este Pokémon");
            } else {
                System.out.println(equipo[opcion].nombre + " te eligió a ti!");
                elegido = true;
            }
        }
    }

    public static void accederMochila(Pokemon[] equipo, Curas[] mochila) {
        Scanner sc = new Scanner(System.in);
        boolean usado = false;

        while (!usado) {
            System.out.println("Elige un objeto:");
            for (int i = 0; i < mochila.length; i++) {
                System.out.println((i+1) + ") " + mochila[i].cantidad + "x " + mochila[i].nombre);
                if (i == mochila.length - 1 ) {
                }
            }

            int opcion = sc.nextInt() - 1;
            if (opcion < 0 || opcion >= mochila.length) continue;
            if (mochila[opcion].cantidad <= 0) {
                System.out.println("No tienes " + mochila[opcion].nombre);
                continue;
            }

            // Mostrar todos los Pokémon vivos del equipo
            System.out.println("¿Qué Pokémon quieres curar?");
            for (int i = 0; i < equipo.length; i++) {
                if (equipo[i].vida > 0) {
                    System.out.println((i+1) + ") " + equipo[i].nombre + " | " + barraVida(equipo[i].vida, equipo[i].vidamax));
                }
            }

            int opcionPokemon = sc.nextInt() - 1;
            if (opcionPokemon < 0 || opcionPokemon >= equipo.length || equipo[opcionPokemon].vida <= 0) {
                System.out.println("Opción inválida.");
                continue;
            }

            // Aplicar la poción
            consumirPocion(equipo[opcionPokemon], mochila[opcion]);
            usado = true;
        }
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

            // Fin del combate
            if (turnoJugador >= equipoJugador.length) {
                System.out.println("¡Has perdido el combate!");
                break;
            }
            if (turnoEnemigo >= equipoEnemigo.length) {
                System.out.println("¡Has ganado el combate!");
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
                System.out.print("Elige tu acción: ");

                int opcion = sc.nextInt();

                switch (opcion) {

                    case 1 -> { // ATACAR
                        System.out.println("Elige movimiento:");
                        for (int i = 0; i < jugador.movimientos.length; i++) {
                            if (jugador.movimientos[i] != null)
                                System.out.println((i + 1) + ") " + jugador.movimientos[i].nombre_atk);
                        }

                        int mov = sc.nextInt() - 1;

                        if (mov >= 0 && mov < jugador.movimientos.length && jugador.movimientos[mov] != null) {
                            jugador.usarMovimiento(mov, enemigo);
                            accionValida = true;
                        } else {
                            System.out.println("Movimiento inválido.");
                        }
                    }

                    case 2 -> { // USAR POCIÓN
                        accederMochila(equipoJugador, mochila);
                        accionValida = true;
                    }

                    case 3 -> { // CAMBIAR POKÉMON
                        System.out.println("Elige Pokémon para cambiar:");

                        for (int i = 0; i < equipoJugador.length; i++) {
                            if (equipoJugador[i].vida > 0 && i != turnoJugador) {
                                System.out.println((i + 1) + ") " + equipoJugador[i].nombre + " | " +
                                        barraVida(equipoJugador[i].vida, equipoJugador[i].vidamax));
                            }
                        }

                        int nuevo = sc.nextInt() - 1;

                        if (nuevo >= 0 && nuevo < equipoJugador.length &&
                                equipoJugador[nuevo].vida > 0 && nuevo != turnoJugador) {

                            System.out.println("¡Cambiando a " + equipoJugador[nuevo].nombre + "!");
                            turnoJugador = nuevo;
                            accionValida = true;

                        } else {
                            System.out.println("Opción inválida.");
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
            }

            System.out.println("\n--- Fin del turno ---\n");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        }
    }
}



