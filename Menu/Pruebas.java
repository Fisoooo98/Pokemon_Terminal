package Menu;
import Items.*;
import Combate.*;
public class Pruebas {
    public static void main(String[] args) {
        Entrenador entrenador = new Entrenador("Fiso",1000,1);
        Curas[] pociones = new Curas[3];{
            pociones[0] = new Pociones();
            pociones[1] = new Superpociones();
            pociones[2] = new Ultrapociones();
        }
        Tienda tienda = new Tienda();
        System.out.println(Entrenador.dinero);
        tienda.AccederTienda(Entrenador.dinero,pociones);
    }
}
