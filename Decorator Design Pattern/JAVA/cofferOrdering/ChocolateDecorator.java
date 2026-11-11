package cofferOrdering;

public class ChocolateDecorator extends CoffeeDecorator{
    public ChocolateDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
       return coffee.getCost()+50;
    }
}
