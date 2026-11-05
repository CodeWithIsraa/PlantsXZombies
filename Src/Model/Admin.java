package Model;

/** A user who can add, edit and delete other users. */
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }
}