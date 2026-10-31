package Model;

/** Zombie wearing a helmet: the strongest zombie, more health and a harder bite. */
public class HelmetZombie extends Zombie {

    public HelmetZombie() {
        super(15, 1, ZombieType.HELMET, 0, 0, 300);
    }

    public HelmetZombie(int x, int y) {
        super(15, 1, ZombieType.HELMET, x, y, 300);
    }
}
