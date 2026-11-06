package Model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menu-driven program that tests all the classes and their methods. Log in
 * first (first time: admin / admin), then choose what to test.
 */
public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final UserManager users = new UserManager(new FileHandler("users.txt"));
    private static User current = null;

    public static void main(String[] args) {
        System.out.println("=== Plants vs Zombies ===");
        while (true) {
            if (current == null && !login()) {
                System.out.println("Bye!");
                return;
            }
            System.out.println("\n===== MAIN MENU (" + current.getUsername() + " - " + current.getRole() + ") =====");
            System.out.println("1) Manage users   2) Test plants   3) Test zombies");
            System.out.println("4) Test Sun / Pea / LawnMower   5) Test Yard   6) Test WaveManager");
            System.out.println("7) Test enums and interfaces   8) Log out   0) Exit");
            switch (ask("> ")) {
                case "1":
                    usersMenu();
                    break;
                case "2":
                    testPlants();
                    break;
                case "3":
                    testZombies();
                    break;
                case "4":
                    testElements();
                    break;
                case "5":
                    testYard();
                    break;
                case "6":
                    testWaves();
                    break;
                case "7":
                    testEnumsAndInterfaces();
                    break;
                case "8":
                    current = null;
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- login ----------
    /**
     * Asks for username and password. Returns false if the user types 0 to
     * exit.
     */
    private static boolean login() {
        while (true) {
            System.out.println("\n-- Login (first time: admin / admin, type 0 to exit)");
            String username = ask("Username: ");
            if (username.equals("0")) {
                return false;
            }
            try {
                current = users.login(username, ask("Password: "));
                return true;
            } catch (UserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ---------- users (add, edit, delete, list) ----------
    private static void usersMenu() {
        while (true) {
            System.out.println("\n-- Users: 1) Add  2) Edit  3) Delete  4) List  0) Back (only admins can add, edit, delete)");
            switch (ask("> ")) {
                case "1":
                    String name = ask("Username: ");
                    String pass = ask("Password: ");
                    User newUser = ask("Role (1=ADMIN, 2=PLAYER): ").equals("1") ? new Admin(name, pass) : new Player(name, pass);
                    try {
                        users.addUser(current, newUser);
                        System.out.println("User added.");
                    } catch (UserException e) {
                        System.out.println("Not added: " + e.getMessage());
                    }
                    break;
                case "2":
                    String old = ask("Username to edit: ");
                    String newName = ask("New username (empty = keep): ");
                    String newPass = ask("New password (empty = keep): ");
                    try {
                        users.editUser(current, old, newName, newPass);
                        System.out.println("User updated.");
                    } catch (UserException e) {
                        System.out.println("Not updated: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        users.deleteUser(current, ask("Username to delete: "));
                        System.out.println("User deleted.");
                    } catch (UserException e) {
                        System.out.println("Not deleted: " + e.getMessage());
                    }
                    break;
                case "4":
                    for (User user : users.listUsers()) {
                        System.out.println(" - " + user.getUsername() + " (" + user.getRole() + ")");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- tests ----------
    private static void testPlants() {
        System.out.println("\n-- Plants");
        ArrayList<Plant> plants = new ArrayList<>();
        plants.add(new Peashooter(1, 2));
        plants.add(new SunFlower(1, 3));
        plants.add(new Potato(1, 4));
        plants.add(new Cherry(1, 5));
        for (Plant plant : plants) {
            System.out.println(plant.getType() + ": cost=" + plant.getCost() + " health=" + plant.getHealth()
                    + " pos=(" + plant.getX() + "," + plant.getY() + ") alive=" + plant.isAlive());
            plant.takeDamage(50);
            System.out.println("  after 50 damage: health=" + plant.getHealth());
            plant.setCost(plant.getCost() + 1);
            plant.setWaitingTime(2.5);
            plant.setHealth(plant.getHealth() + 10);
            plant.setX(0);
            plant.setY(0);
            System.out.println("  after setters: cost=" + plant.getCost() + " waitingTime=" + plant.getWaitingTime()
                    + " health=" + plant.getHealth() + " pos=(" + plant.getX() + "," + plant.getY() + ")");
        }
        Peashooter shooter = (Peashooter) plants.get(0);
        shooter.action();
        System.out.println("Peashooter fired, peas in the air: " + shooter.getPeas().size());
        System.out.println("SunFlower produced a sun worth " + ((SunFlower) plants.get(1)).produceSun().getValue());
        Cherry cherry = (Cherry) plants.get(3);
        cherry.setBlastRadius(5);
        System.out.println("Cherry blast radius: " + cherry.getBlastRadius());
        cherry.action();
        System.out.println("Cherry exploded, active=" + cherry.isActive());
        Potato potato = (Potato) plants.get(2);
        potato.takeDamage(5000);
        System.out.println("Potato after huge damage: alive=" + potato.isAlive() + " active=" + potato.isActive());
    }

    private static void testZombies() {
        System.out.println("\n-- Zombies");
        ArrayList<Zombie> zombies = new ArrayList<>();
        zombies.add(new DefaultZombie(8, 1));
        zombies.add(new ConeZombie(8, 2));
        zombies.add(new HelmetZombie(8, 3));
        for (Zombie zombie : zombies) {
            System.out.println(zombie.getType() + ": health=" + zombie.getHealth() + " attack=" + zombie.getAttackPower()
                    + " speed=" + zombie.getSpeed() + " x=" + zombie.getX());
            zombie.move();
            zombie.action();
            System.out.println("  after move + action: x=" + zombie.getX());
            zombie.setSpeed(2);
            zombie.setAttackPower(20);
            zombie.takeDamage(150);
            System.out.println("  speed=" + zombie.getSpeed() + " attack=" + zombie.getAttackPower()
                    + " health=" + zombie.getHealth() + " alive=" + zombie.isAlive());
        }
    }

    private static void testElements() {
        System.out.println("\n-- Sun / Pea / LawnMower");
        Sun sun = new Sun(3, 0);
        sun.move();
        System.out.println("Sun value=" + sun.getValue() + " fell to y=" + sun.getY());
        sun.disappear();
        System.out.println("Sun after disappear: active=" + sun.isActive());
        Pea pea = new Pea(1, 1, 20);
        pea.moveForward();
        System.out.println("Pea damage=" + pea.getDamage() + " x=" + pea.getX() + " active=" + pea.isActive());
        pea.hit();
        System.out.println("Pea after hit: active=" + pea.isActive());
        LawnMower mower = new LawnMower(0, 2);
        mower.move();
        System.out.println("Mower before activate: x=" + mower.getX());
        mower.activate();
        mower.move();
        System.out.println("Mower after activate: x=" + mower.getX());
    }

    private static void testYard() {
        System.out.println("\n-- Yard");
        Yard yard = new Yard();
        try {
            yard.placePlant(new Peashooter(), 2, 1);
            yard.placePlant(new Potato(), 2, 4);
            System.out.println("Placed a peashooter and a potato.");
            yard.placePlant(new SunFlower(), 2, 1);
        } catch (InvalidPositionException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            yard.placePlant(new SunFlower(), 9, 9);
        } catch (InvalidPositionException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        yard.addZombie(new DefaultZombie(8, 2));
        for (int turn = 1; turn <= 4; turn++) {
            int sun = yard.plantsAct();
            yard.peasMove();
            boolean gameOver = yard.zombiesAct();
            System.out.println("Turn " + turn + ": sun=" + sun + " gameOver=" + gameOver + " killed=" + yard.getKilledZombies());
        }
        yard.display();
        System.out.println("Plant at (3,2): " + yard.getPlant(2, 1) + ", valid position (9,9): " + yard.isValidPosition(9, 9));
        System.out.println("Remove plant at (3,2): " + yard.removePlant(2, 1) + ", again: " + yard.removePlant(2, 1));
        System.out.println("Zombies left: " + yard.getZombies().size() + ", hasZombies=" + yard.hasZombies());
    }

    private static void testWaves() {
        System.out.println("\n-- WaveManager");
        ArrayList<ZombieType> types = new ArrayList<>();
        types.add(ZombieType.DEFAULT);
        types.add(ZombieType.CONE);
        types.add(ZombieType.HELMET);
        WaveManager waves = new WaveManager(4, 2, types);
        for (int turn = 1; !waves.isFinished(); turn++) {
            Zombie zombie = waves.spawnIfNeeded(turn);
            if (zombie != null) {
                System.out.println("Turn " + turn + ": " + zombie.getType() + " at row " + (zombie.getY() + 1));
            }
        }
        System.out.println("Wave finished: " + waves.getSpawned() + "/" + waves.getTotalZombies());
    }

    private static void testEnumsAndInterfaces() {
        System.out.println("\n-- Enums");
        for (PlantType type : PlantType.values()) {
            System.out.println("PlantType: " + type);
        }
        for (ZombieType type : ZombieType.values()) {
            System.out.println("ZombieType: " + type);
        }
        for (UserRole role : UserRole.values()) {
            System.out.println("UserRole: " + role);
        }
        System.out.println("-- Interfaces");
        Movable movable = new DefaultZombie(5, 0);
        movable.move();
        System.out.println("Movable (zombie) moved to x=" + ((DefaultZombie) movable).getX());
        Attacker attacker = new Peashooter(0, 0);
        attacker.action();
        System.out.println("Attacker (peashooter) attacked, peas=" + ((Peashooter) attacker).getPeas().size());
        Storable storable = current;
        System.out.println("Storable (current user) as a file line: " + storable.toFileLine());
    }

    private static String ask(String text) {
        System.out.print(text);
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }
}
