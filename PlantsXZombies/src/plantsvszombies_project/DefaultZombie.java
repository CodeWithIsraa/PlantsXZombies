package plantsvszombies_project;

/** The normal zombie: no protection. */
public class DefaultZombie extends Zombie {

    public DefaultZombie() {
        super(10, 1, ZombieType.DEFAULT, 0, 0, 100);
    }

    public DefaultZombie(int x, int y) {
        super(10, 1, ZombieType.DEFAULT, x, y, 100);
    }
}
