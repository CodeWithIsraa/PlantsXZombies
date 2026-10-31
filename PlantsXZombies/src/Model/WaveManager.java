package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Decides when and where zombies appear.
 * A new zombie enters at the right edge every few turns until the wave is finished.
 */
public class WaveManager {

    private final int totalZombies;
    private final int spawnEveryTurns;
    private final ArrayList<ZombieType> allowedTypes;
    private final Random random = new Random();
    private int spawned;

    public WaveManager(int totalZombies, int spawnEveryTurns, ArrayList<ZombieType> allowedTypes) {
        this.totalZombies = totalZombies;
        this.spawnEveryTurns = spawnEveryTurns;
        this.allowedTypes = allowedTypes;
        this.spawned = 0;
    }

    /** Returns the zombie that appears on this turn, or null if nobody comes now. */
    public Zombie spawnIfNeeded(int turn) {
        if (isFinished() || (turn - 1) % spawnEveryTurns != 0) {
            return null;
        }
        ZombieType type = allowedTypes.get(random.nextInt(allowedTypes.size()));
        int row = random.nextInt(Yard.ROWS);
        int col = Yard.COLS - 1;
        spawned++;
        switch (type) {
            case CONE:
                return new ConeZombie(col, row);
            case HELMET:
                return new HelmetZombie(col, row);
            default:
                return new DefaultZombie(col, row);
        }
    }

    /** True when every zombie of the wave has already appeared. */
    public boolean isFinished() {
        return spawned >= totalZombies;
    }

    public int getTotalZombies() {
        return totalZombies;
    }

    public int getSpawned() {
        return spawned;
    }
}
