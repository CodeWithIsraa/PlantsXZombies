package Model;

import java.util.ArrayList;

/**
 * The board: a ROWS x COLS grid of plants, one lawn mower per row,
 * and the zombies and peas that are currently on the lawn.
 * x is the column and y is the row (same as the rest of the project).
 */
public class Yard {

    public static final int ROWS = 5;
    public static final int COLS = 9;
    private static final int PEA_STEPS_PER_TURN = 2;
    private static final int CHERRY_DAMAGE = 300;

    private final Plant[][] grid = new Plant[ROWS][COLS];
    private final LawnMower[] lawnMowers = new LawnMower[ROWS];
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Pea> peas = new ArrayList<>();
    private int killedZombies = 0;

    public Yard() {
        for (int row = 0; row < ROWS; row++) {
            lawnMowers[row] = new LawnMower(0, row);
        }
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public void placePlant(Plant plant, int row, int col) throws InvalidPositionException {
        if (!isValidPosition(row, col)) {
            throw new InvalidPositionException("Position (" + (row + 1) + ", " + (col + 1) + ") is outside the yard.");
        }
        if (grid[row][col] != null) {
            throw new InvalidPositionException("Position (" + (row + 1) + ", " + (col + 1) + ") is already occupied.");
        }
        plant.setX(col);
        plant.setY(row);
        grid[row][col] = plant;
    }

    /** Removes the plant in the cell. Returns true if there was a plant. */
    public boolean removePlant(int row, int col) {
        if (isValidPosition(row, col) && grid[row][col] != null) {
            grid[row][col] = null;
            return true;
        }
        return false;
    }

    public Plant getPlant(int row, int col) {
        return isValidPosition(row, col) ? grid[row][col] : null;
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public boolean hasZombies() {
        return !zombies.isEmpty();
    }

    public int getKilledZombies() {
        return killedZombies;
    }

    /**
     * Every plant does its job. Returns the sun produced by the sunflowers.
     * Potatoes are walls and do nothing.
     */
    public int plantsAct() {
        int sun = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Plant plant = grid[row][col];
                if (plant == null) {
                    continue;
                }
                if (plant instanceof SunFlower) {
                    sun += ((SunFlower) plant).produceSun().getValue();
                } else if (plant instanceof Peashooter) {
                    if (zombieAhead(row, col)) {
                        Peashooter shooter = (Peashooter) plant;
                        shooter.action();
                        peas.addAll(shooter.getPeas());
                        shooter.getPeas().clear();
                    }
                } else if (plant instanceof Cherry) {
                    plant.action();
                    blast((Cherry) plant, row, col);
                }
            }
        }
        cleanUp();
        return sun;
    }

    /** Peas fly forward and hit the first zombie they reach. */
    public void peasMove() {
        for (int step = 0; step < PEA_STEPS_PER_TURN; step++) {
            for (Pea pea : peas) {
                if (!pea.isActive()) {
                    continue;
                }
                pea.moveForward();
                Zombie target = firstZombieReachedBy(pea);
                if (target != null) {
                    target.takeDamage(pea.getDamage());
                    pea.hit();
                } else if (pea.getX() >= COLS) {
                    pea.hit();
                }
            }
        }
        peas.removeIf(pea -> !pea.isActive());
        cleanUp();
    }

    /**
     * Every zombie bites the plant in front of it, or walks if the way is free.
     * A zombie that reaches the house triggers the lawn mower of its row.
     * Returns true if a zombie reached the house with no mower left (game over).
     */
    public boolean zombiesAct() {
        for (Zombie zombie : new ArrayList<>(zombies)) {
            if (!zombie.isAlive()) {
                continue;
            }
            int row = zombie.getY();
            int col = zombie.getX();
            Plant front = getPlant(row, col - 1);
            if (front != null && front.isActive()) {
                front.takeDamage(zombie.getAttackPower());
            } else {
                zombie.action();
            }
            if (zombie.getX() <= 0 && !useLawnMower(row)) {
                return true;
            }
        }
        cleanUp();
        return false;
    }

    /** Prints the board. Plants are letters, zombies are letters on the right of the cell. */
    public void display() {
        System.out.print("         ");
        for (int col = 1; col <= COLS; col++) {
            System.out.print(" " + col + "  ");
        }
        System.out.println();
        for (int row = 0; row < ROWS; row++) {
            System.out.print("Row " + (row + 1) + (lawnMowers[row] != null ? " M" : "  ") + " |");
            for (int col = 0; col < COLS; col++) {
                System.out.print(plantLetter(grid[row][col]) + "" + zombieLetterAt(row, col) + " |");
            }
            System.out.println();
        }
        System.out.println("Plants: P=Peashooter S=Sunflower C=Cherry O=Potato   Zombies: D=Default N=Cone H=Helmet   M=Lawn mower");
    }

    // ---------- private helpers ----------

    private boolean zombieAhead(int row, int col) {
        for (Zombie zombie : zombies) {
            if (zombie.isAlive() && zombie.getY() == row && zombie.getX() > col) {
                return true;
            }
        }
        return false;
    }

    private Zombie firstZombieReachedBy(Pea pea) {
        Zombie best = null;
        for (Zombie zombie : zombies) {
            if (zombie.isAlive() && zombie.getY() == pea.getY() && zombie.getX() <= pea.getX()) {
                if (best == null || zombie.getX() > best.getX()) {
                    best = zombie;
                }
            }
        }
        return best;
    }

    /** The cherry's blast radius is its size, so 3 means a 3x3 area around it. */
    private void blast(Cherry cherry, int row, int col) {
        int reach = cherry.getBlastRadius() / 2;
        for (Zombie zombie : zombies) {
            if (Math.abs(zombie.getY() - row) <= reach && Math.abs(zombie.getX() - col) <= reach) {
                zombie.takeDamage(CHERRY_DAMAGE);
            }
        }
    }

    /** Kills every zombie in the row and uses up the mower. Returns false if the mower is already used. */
    private boolean useLawnMower(int row) {
        LawnMower mower = lawnMowers[row];
        if (mower == null) {
            return false;
        }
        mower.activate();
        for (Zombie zombie : zombies) {
            if (zombie.getY() == row) {
                zombie.takeDamage(Integer.MAX_VALUE / 2);
            }
        }
        lawnMowers[row] = null;
        System.out.println("The lawn mower in row " + (row + 1) + " cleaned the row!");
        return true;
    }

    /** Removes dead plants and dead zombies from the board. */
    private void cleanUp() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Plant plant = grid[row][col];
                if (plant != null && (!plant.isActive() || !plant.isAlive())) {
                    grid[row][col] = null;
                }
            }
        }
        ArrayList<Zombie> dead = new ArrayList<>();
        for (Zombie zombie : zombies) {
            if (!zombie.isActive() || !zombie.isAlive()) {
                dead.add(zombie);
            }
        }
        killedZombies += dead.size();
        zombies.removeAll(dead);
    }

    private char plantLetter(Plant plant) {
        if (plant == null) {
            return ' ';
        }
        switch (plant.getType()) {
            case PEASHOOTER:
                return 'P';
            case SUNFLOWER:
                return 'S';
            case CHERRY:
                return 'C';
            default:
                return 'O';
        }
    }

    private char zombieLetterAt(int row, int col) {
        for (Zombie zombie : zombies) {
            if (zombie.getY() == row && zombie.getX() == col) {
                switch (zombie.getType()) {
                    case CONE:
                        return 'N';
                    case HELMET:
                        return 'H';
                    default:
                        return 'D';
                }
            }
        }
        return ' ';
    }
}
