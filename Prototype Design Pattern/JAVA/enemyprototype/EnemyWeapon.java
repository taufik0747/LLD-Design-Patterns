package enemyprototype;

class EnemyWeapon implements Prototype<EnemyWeapon> {

    private final String name;
    private final Weapon weapon;

    public EnemyWeapon(String name, Weapon weapon) {
        this.name = name;
        this.weapon = weapon;
    }

    @Override
    public EnemyWeapon copy() {
        EnemyWeapon copied = new EnemyWeapon(name, this.weapon.copy());
      return copied;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public static void main(String[] args) {

        Weapon sword = new Weapon("Sword", 50);
        EnemyWeapon original = new EnemyWeapon("Knight", sword);

        EnemyWeapon copied = original.copy();
        copied.getWeapon().setDamage(100);

        System.out.println(original == copied);
        System.out.println(original.getWeapon() == copied.getWeapon());
        System.out.println(original.getWeapon().getDamage());
        System.out.println(copied.getWeapon().getDamage());
    }
}
