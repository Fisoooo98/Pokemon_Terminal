package Pokemon;
public class Squirtle extends Pokemon {
    public Squirtle() {
        //Stats squirtle por defecto
        super(BOLD + YELLOW + "Squirtle" + RESET, 35, 35, 20, 10, BOLD + BLUE + "agua" + RESET,5,500);
        movimientos[0] = new Ataque("Pistola Agua", "agua", 30);
        movimientos[1] = new Ataque("Placaje", "normal", 20);
    }
    @Override
    public void movimiento1(Pokemon enemigo) {
        usarMovimiento(0, enemigo);
    }

    @Override
    public void movimiento2(Pokemon enemigo) {
        usarMovimiento(1, enemigo);
    }

}
