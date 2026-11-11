package cofferOrdering;

public class Main {
    public static void main(String[] args) {
        Coffee coffee = new BasicCoffee();
        CoffeeDecorator coffeeDecorator = new MilkDecorator(coffee);
        coffeeDecorator = new ChocolateDecorator(coffeeDecorator);
        System.out.println(coffeeDecorator.getCost());
    }
}
