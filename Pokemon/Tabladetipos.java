package Pokemon;
import java.util.HashMap;
public class Tabladetipos {
    //Creacion un diccionario para la tabla de tipos
    public static HashMap<String, HashMap<String, Double>> chart = new HashMap<>();
    static {
        //Tipos de ataques
        HashMap<String, Double> normal = new HashMap<>();//Tipo normal
        normal.put("agua", 1.0);
        normal.put("fuego", 1.0);
        normal.put("planta", 1.0);
        normal.put("normal",1.0);
        HashMap<String, Double> agua = new HashMap<>(); //Tipo agua
        agua.put("agua", 1.0);
        agua.put("fuego", 2.0);
        agua.put("planta", 0.5);
        agua.put("normal",1.0);

        HashMap<String, Double> fuego = new HashMap<>(); //Tipo fuego
        fuego.put("agua", 0.5);
        fuego.put("fuego", 1.0);
        fuego.put("planta", 2.0);
        fuego.put("normal",1.0);

        HashMap<String, Double> planta = new HashMap<>(); //Tipo planta
        planta.put("agua", 2.0);
        planta.put("fuego", 0.5);
        planta.put("planta", 1.0);
        planta.put("normal",1.0);

        //Definimos cada regla con su correspondiente variable
        chart.put("agua", agua);
        chart.put("fuego", fuego);
        chart.put("planta", planta);
        chart.put("normal", normal);
    }
    //Para obtener que multiplicador del ataque segun el tipo
    public static double multiplicador(String ataque, String defensa) {
        String atk = ataque.toLowerCase().replaceAll("\u001B\\[[;\\d]*m", "");
        String def = defensa.toLowerCase().replaceAll("\u001B\\[[;\\d]*m", "");

        return chart.getOrDefault(atk, chart.get("normal")).getOrDefault(def, 1.0);
    }
}
