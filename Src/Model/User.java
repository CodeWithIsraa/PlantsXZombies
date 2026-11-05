/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Ali El-Maghraby
 */
public abstract class User implements Storable {

    // Attributes
    protected String username;
    protected String password;

    // Default Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Check Password
    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    // Get User Role
    public abstract UserRole getRole();

    // Check if the user is an admin
    public boolean isAdmin() {
        return getRole() == UserRole.ADMIN;
    }

    // Convert User Data to String
    @Override
    public String toFileLine() {
        return username + "," + password + "," + getRole();
    }
}