package Menu;
import Items.*;
import Combate.*;

import java.util.Scanner;

public class Tienda {
    Curas[] pociones = new Curas[3];{
        pociones[0] = new Pociones();
        pociones[1] = new Superpociones();
        pociones[2] = new Ultrapociones();
    }
    public static void AccederTienda(int Dinero,Curas[]pociones) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Has accedido a la tienda");
        //Imprime las curas
        for (int i = 0; i < pociones.length; i++) {
            System.out.println(i + 1 + " " + pociones[i].cantidad + "x " + pociones[i].nombre + "  " + pociones[i].costo + "€");
        }
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1 -> {
                pociones[0].cantidad +=1;
                Dinero -= pociones[0].costo;
            }
            case 2 -> {
                pociones[1].cantidad +=1;
                Dinero -= pociones[1].costo;
            }
            case 3 -> {
                pociones[2].cantidad +=1;
                Dinero -= pociones[2].costo;
            }
        }
        System.out.println("Has comprado " + pociones[opcion - 1].nombre);
        System.out.println("Dinero actual " + Dinero + "€");
    }
}
