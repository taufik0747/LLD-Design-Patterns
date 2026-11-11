package beverage;

public class Main {
    public static void main(String[] args) {
        Beverage beverage = new Tea();
        beverage.prepare();

        Beverage coffee = new Coffee();
        coffee.prepare();
    }
}
