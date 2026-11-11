package beverage;

public abstract class Beverage {

    public final void prepare(){
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    private void boilWater(){
        System.out.println("Boiling water");
    }

    protected abstract void brew();

    private void pourInCup(){
        System.out.println("Pouring into cup");
    }

    protected abstract void addCondiments();

}
