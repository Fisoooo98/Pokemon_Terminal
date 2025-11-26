package Pokemon;

import java.util.Scanner;

public abstract class Pokemon {
    //Movimientos

    //Colores
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    //------------------------------------------------------------------------------------
    //Tipos de letra
    public static final String BOLD = "\u001B[1m";
    public String nombre;
    public int vida;
    public int vidamax;
    public int ataque;
    public int defensa;
    public String tipo;
    public int nivel;
    public int xp_actual;

    Pokemon(String nombre, int vida, int vidamax, int ataque, int defensa, String tipo,int nivel,int xp_actual) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidamax = vidamax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.tipo = tipo;
        this.nivel = nivel;
        this.xp_actual = xp_actual;
    }


    //Movimientos en la clase
    public Ataque[] movimientos = new Ataque[4];
    public abstract void movimiento1(Pokemon enemigo);
    public abstract void movimiento2(Pokemon enemigo);

    //Creamos un metodo que va a servir para atacar
    public void UsarAtaque(Ataque atk, Pokemon enemigo) {
        System.out.println(nombre + " usa " + atk.nombre_atk + " !");
        double multiplicador = Tabladetipos.multiplicador(atk.tipo_atk, enemigo.tipo); //Usamos el tipo de ataque y la el tipo que es el pokemon
        double damage = ((ataque + atk.ataque_atk) - enemigo.defensa) * multiplicador;
        enemigo.vida -= damage;
        if (multiplicador > 1) {
            System.out.println("Es muy eficaz!!");
        } else if (multiplicador == 1) {
            System.out.println("Es efectivo");
        } else if (multiplicador < 1) {
            System.out.println("Es poco eficaz");
        } else {
            System.out.println("No le ha dado");
        }
        System.out.println("Daño causado: " + damage);
        if (enemigo.vida < 0) {
            System.out.println(enemigo.nombre + " se ha debilitado");
        }
    }


    public void usarMovimiento(int index, Pokemon enemigo) {
        Ataque atk = movimientos[index];
        UsarAtaque(atk, enemigo);
    }

    public boolean aprenderMovimiento(Ataque nuevo) {
        // 1. Comprobar si hay hueco libre
        for (int i = 0; i < movimientos.length; i++) {
            if (movimientos[i] == null) {
                movimientos[i] = nuevo;
                System.out.println(nombre + " aprendió " + nuevo.nombre_atk + "!");
                return true;
            }
        }

        // 2. Si no hay hueco, preguntar si quiere olvidar uno
        System.out.println(nombre + " quiere aprender " + nuevo.nombre_atk + ", pero ya conoce 4 movimientos.");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("¿Quieres olvidar un movimiento para aprenderlo? (s/n)");
            String resp = sc.nextLine().trim();

            if (resp.equalsIgnoreCase("n")) {
                System.out.println(nombre + " decidió no aprender " + nuevo.nombre_atk + ".");
                return false;
            } else if (resp.equalsIgnoreCase("s")) {
                // Mostrar movimientos
                System.out.println("Elige el movimiento a olvidar:");
                for (int i = 0; i < movimientos.length; i++) {
                    System.out.println((i+1) + ") " + movimientos[i].nombre_atk);
                }

                int pos;
                while (true) {
                    System.out.print("Número del movimiento a olvidar: ");
                    pos = sc.nextInt() - 1;
                    sc.nextLine(); // limpiar buffer
                    if (pos < 0 || pos >= movimientos.length) {
                        System.out.println("Opción inválida, intenta de nuevo.");
                        continue;
                    }

                    // Confirmación antes de borrar
                    System.out.println("¿Seguro que quieres olvidar " + movimientos[pos].nombre_atk + "? (s/n)");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("s")) {
                        System.out.println(nombre + " olvidó " + movimientos[pos].nombre_atk + ".");
                        movimientos[pos] = nuevo;
                        System.out.println(nombre + " aprendió " + nuevo.nombre_atk + "!");
                        return true;
                    } else {
                        System.out.println("Cancelado. No se cambió ningún movimiento.");
                        return false;
                    }
                }
            } else {
                System.out.println("Respuesta inválida, escribe 's' o 'n'.");
            }
        }
    }
    public int xpNivel(int nivel) {
        return nivel * nivel * nivel;
    }
    public void subirNivel(int xp_adquirida) {
        this.xp_actual += xp_adquirida;
        while(this.xp_actual >= xpNivel(this.nivel + 1)) {
                int xp_necesaria =  xpNivel(this.nivel + 1);
                this.nivel ++;
                this.vidamax += 3;
                this.ataque += 2;
                this.defensa += 2;
            System.out.println(this.nombre + " ha subido a nivel " + this.nivel + " !");
        }

        int SiguienteNivel = xpNivel(this.nivel + 1) - this.xp_actual;
        System.out.println(this.nombre + " necesita " + SiguienteNivel + " XP para el siguiente nivel.");
    }
    public int calcularXP(Pokemon enemigo) {
        return enemigo.nivel * 20;
    }
    public void ganarXP(Pokemon enemigo) {
        int xpGanada = calcularXP(enemigo);

        subirNivel(xpGanada);

        System.out.println(nombre + " ganó " + xpGanada + " puntos de experiencia!");

    }

}
