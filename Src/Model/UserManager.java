package Model;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;
    private FileHandler fileHandler;

    /**
     * Constructor initializing UserManager with a FileHandler 
     * and loading existing users from the file.
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

    /**
     * Adds a new user if the requester has administrative privileges.
     */
    public boolean addUser(User requester, User user) {
        // Simple permission check: requester must be an Admin
        if (requester == null || !requester.isAdmin()) {
            return false;
        }

        // Prevent duplicate usernames
        if (findUser(user.getUsername()) != null) {
            return false;
        }

        users.add(user);
        save();
        return true;
    }

    /**
     * Edits a user's details if the requester has administrative privileges.
     */
    public boolean editUser(User requester, String username, String newUsername, String newPassword) {
        if (requester == null || !requester.isAdmin()) {
            return false;
        }

        User existingUser = findUser(username);
        if (existingUser == null) {
            return false;
        }

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            existingUser.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            existingUser.setPassword(newPassword);
        }

        save();
        return true;
    }

    /**
     * Deletes a user by username if the requester has administrative privileges.
     */
    public boolean deleteUser(User requester, String username) {
        if (requester == null || !requester.isAdmin()) {
            return false;
        }

        User existingUser = findUser(username);
        if (existingUser != null) {
            users.remove(existingUser);
            save();
            return true;
        }

        return false;
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
    public User login(String username, String password) {
        User u = findUser(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
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