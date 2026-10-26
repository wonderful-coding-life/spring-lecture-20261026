public class SpringCafe {
    public static void main(String[] args) {
        Barista barista = new Barista();
        barista.setCoffeeMachine(new EspressoMachine());
        barista.makeCoffee();
    }
}
