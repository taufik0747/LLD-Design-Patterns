package enemyprototype;

import java.util.ArrayList;
import java.util.List;

public class Enemy implements Prototype<Enemy>{

    private final String name;
    private int health;
   private List<String> abilities;

    public Enemy(String name, int health){
        this.name=name;
        this.health=health;
        this.abilities=new ArrayList<>();
    }

    public void addAbility(String ability){
        abilities.add(ability);

    }

    @Override
    public Enemy copy() {
        Enemy copied = new Enemy(name, health);
        copied.abilities = new ArrayList<>(this.abilities);
        return copied;

    }

    public List<String> getAbilities(){
        return new ArrayList<>(abilities);
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {

        Enemy original = new Enemy("Dragon", 1000);
        original.addAbility("Fire");

        Enemy copied = original.copy();
        copied.addAbility("Ice");

        System.out.println(original == copied);
        System.out.println(original.getAbilities());
        System.out.println(copied.getAbilities());
    }
}
