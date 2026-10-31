package plantsvszombies_project;

/** Zombie wearing a cone: twice the health of a default zombie. */
public class ConeZombie extends Zombie {

    public ConeZombie() {
        super(10, 1, ZombieType.CONE, 0, 0, 200);
    }

    public ConeZombie(int x, int y) {
        super(10, 1, ZombieType.CONE, x, y, 200);
    }
}
