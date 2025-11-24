package Items;

public abstract class Curas {
    public static final String PURPLE = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String BLACK = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
    //Clase curas para definir las pociones.
    public String nombre;
    public int vida;
    public int costo;
    public int cantidad;
    Curas(String nombre, int vida, int costo, int cantidad) {
        this.nombre = nombre;
        this.vida = vida;
        this.costo = costo;
        this.cantidad = cantidad;
    }
}
