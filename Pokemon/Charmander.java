package Pokemon;
public class Charmander extends Pokemon {
    public Charmander() {
        //Las stats del charmander por defecto
    super(BOLD + YELLOW + "Charmander" + RESET,  35, 35, 15, 10,BOLD + ORANGE + "fuego" + RESET,5,500);
        movimientos[0] = new Ataque("Ascuas", "fuego", 25);
        movimientos[1] = new Ataque("Arañazo", "normal", 15);
    }
    @Override
    public void movimiento1(Pokemon enemigo) {
        usarMovimiento(0, enemigo);
    }

    @Override
    public void movimiento2(Pokemon enemigo) {
        usarMovimiento(1, enemigo);
    }
    public void subirNivel(){

    }
}
