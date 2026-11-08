package enemyprototype;

public class Weapon implements Prototype<Weapon>{
    private final String name;
    private int damage;

    public Weapon(String name, int damage){
        this.name=name;
        this.damage=damage;
    }

    @Override
    public Weapon copy(){
        Weapon weapon = new Weapon(name,damage);
        return weapon;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}
