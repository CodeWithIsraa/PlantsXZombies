package Model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs one game in the console: the player plants, then ends the turn
 * and the plants, peas and zombies move.
 */
public class Game {

    private static final int START_SUN = 150;
    private static final int SKY_SUN_PER_TURN = 25;

    private final Scanner in;
    private final Yard yard;
    private final WaveManager waves;
    private int sun;
    private int turn;

    public Game(Scanner in) {
        this(in, defaultWave());
    }

    public Game(Scanner in, WaveManager waves) {
        this.in = in;
        this.yard = new Yard();
        this.waves = waves;
        this.sun = START_SUN;
        this.turn = 1;
    }

    private static WaveManager defaultWave() {
        ArrayList<ZombieType> types = new ArrayList<>();
        types.add(ZombieType.DEFAULT);
        types.add(ZombieType.CONE);
        types.add(ZombieType.HELMET);
        return new WaveManager(8, 2, types);
    }

    /** Plays until the player wins, loses or quits. Returns true if the player won. */
    public boolean play() {
        System.out.println("=== Plants vs Zombies ===");
        System.out.println("Kill all " + waves.getTotalZombies() + " zombies. Do not let them reach your house.");

        while (true) {
            System.out.println();
            System.out.println("--- Turn " + turn + " ---");
            yard.display();
            System.out.println("Sun: " + sun + "   Zombies killed: " + yard.getKilledZombies() + "/" + waves.getTotalZombies());

            if (!playerPhase()) {
                System.out.println("Game stopped.");
                return false;
            }

            sun += SKY_SUN_PER_TURN + yard.plantsAct();
            yard.peasMove();
            if (yard.zombiesAct()) {
                System.out.println();
                System.out.println("A zombie reached your house!");
                System.out.println("=== GAME OVER ===");
                return false;
            }

            Zombie newcomer = waves.spawnIfNeeded(turn);
            if (newcomer != null) {
                yard.addZombie(newcomer);
                System.out.println("A " + newcomer.getType() + " zombie appeared in row " + (newcomer.getY() + 1) + "!");
            }

            if (waves.isFinished() && !yard.hasZombies()) {
                System.out.println();
                System.out.println("=== YOU WIN! ===");
                return true;
            }
            turn++;
        }
    }

    /** The player's menu for one turn. Returns false if the player quits. */
    private boolean playerPhase() {
        while (true) {
            System.out.println("1) Plant   2) Shovel   3) End turn   0) Quit");
            System.out.print("> ");
            if (!in.hasNextLine()) {
                return false;
            }
            switch (in.nextLine().trim()) {
                case "1":
                    plantMenu();
                    break;
                case "2":
                    shovelMenu();
                    break;
                case "3":
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void plantMenu() {
        System.out.println("1) Peashooter (" + new Peashooter().getCost() + ")   2) SunFlower (" + new SunFlower().getCost()
                + ")   3) Potato (" + new Potato().getCost() + ")   4) Cherry (" + new Cherry().getCost() + ")");
        System.out.print("Plant> ");
        if (!in.hasNextLine()) {
            return;
        }
        Plant plant;
        switch (in.nextLine().trim()) {
            case "1":
                plant = new Peashooter();
                break;
            case "2":
                plant = new SunFlower();
                break;
            case "3":
                plant = new Potato();
                break;
            case "4":
                plant = new Cherry();
                break;
            default:
                System.out.println("Invalid plant.");
                return;
        }
        if (sun < plant.getCost()) {
            System.out.println("Not enough sun! You need " + plant.getCost() + ".");
            return;
        }
        int[] cell = readCell();
        if (cell == null) {
            return;
        }
        try {
            yard.placePlant(plant, cell[0], cell[1]);
            sun -= plant.getCost();
            System.out.println(plant.getType() + " planted.");
        } catch (InvalidPositionException e) {
            System.out.println("Cannot plant: " + e.getMessage());
        }
    }

    private void shovelMenu() {
        int[] cell = readCell();
        if (cell == null) {
            return;
        }
        System.out.println(yard.removePlant(cell[0], cell[1]) ? "Plant removed." : "There is no plant there.");
    }

    /** Reads "row col" (starting from 1) and returns {row, col} starting from 0, or null. */
    private int[] readCell() {
        System.out.print("Row (1-" + Yard.ROWS + ") and column (1-" + Yard.COLS + ")> ");
        if (!in.hasNextLine()) {
            return null;
        }
        String[] parts = in.nextLine().trim().split("\\s+");
        if (parts.length != 2) {
            System.out.println("Please type two numbers, for example: 3 2");
            return null;
        }
        try {
            return new int[]{Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]) - 1};
        } catch (NumberFormatException e) {
            System.out.println("Please type numbers only.");
            return null;
        }
    }
}
