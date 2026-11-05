package Model;

/** A normal user who only plays. */
public class Player extends User {

    public Player(String username, String password) {
        super(username, password);
    }

    @Override
    public UserRole getRole() {
        return UserRole.PLAYER;
    }
}