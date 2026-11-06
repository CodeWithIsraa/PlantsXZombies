package Model;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> users;
    private FileHandler fileHandler;

    /**
     * Constructor initializing UserManager with a FileHandler and loading
     * existing users from the file.
     */
    public UserManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        // Assuming fileHandler has a method readUsers() or loadUsers() that returns ArrayList<User>
        this.users = fileHandler.readUsers();
        if (this.users == null) {
            this.users = new ArrayList<User>();
        }
    }

    /**
     * Finds and returns a user by username.
     */
    public User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

   
    public void addUser(User requester, User user) throws UserException {
        if (requester == null || !requester.isAdmin()) {
            throw new UserException("Only an admin can add users.");
        }

        if (findUser(user.getUsername()) != null) {
            throw new UserException("Username already exists: " + user.getUsername());
        }

        users.add(user);
        save();
    }

    public void editUser(User requester, String username, String newUsername, String newPassword) throws UserException {
        if (requester == null || !requester.isAdmin()) {
            throw new UserException("Only an admin can edit users.");
        }

        User existingUser = findUser(username);
        if (existingUser == null) {
            throw new UserException("User not found: " + username);
        }

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            existingUser.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            existingUser.setPassword(newPassword);
        }

        save();
    }

    public void deleteUser(User requester, String username) throws UserException {
        if (requester == null || !requester.isAdmin()) {
            throw new UserException("Only an admin can delete users.");
        }

        User existingUser = findUser(username);
        if (existingUser == null) {
            throw new UserException("User not found: " + username);
        }

        users.remove(existingUser);
        save();
    }

    /**
     * Returns the list of all users.
     */
    public ArrayList<User> listUsers() {
        return users;
    }

    /**
     * Authenticates a user given username and password.
     */
  public User login(String username, String password) throws UserException {
    User u = findUser(username);
    if (u == null || !u.getPassword().equals(password)) {
        throw new UserException("Wrong username or password.");
    }
    return u;
}

    /**
     * Saves the current list of users to storage via FileHandler.
     */
    public void save() {
        if (fileHandler != null) {
            fileHandler.writeUsers(users);
        }
    }
}
