package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/** Reads and writes users from a text file, one user per line: username,password,ROLE */
public class FileHandler {

    private final String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads all users from the file.
     * If there are no users yet, creates a default admin (admin / admin),
     * so there is someone who can add the first users.
     */
    public ArrayList<User> readUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = in.readLine()) != null) {
                    User user = parseLine(line.trim());
                    if (user != null) {
                        users.add(user);
                    }
                }
            } catch (IOException e) {
                System.out.println("Could not read users: " + e.getMessage());
            }
        }
        if (users.isEmpty()) {
            users.add(new Admin("admin", "admin"));
            writeUsers(users);
        }
        return users;
    }

    /** Writes all users to the file. */
    public void writeUsers(ArrayList<User> users) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (User user : users) {
                out.println(user.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save users: " + e.getMessage());
        }
    }

    /** Builds a user from one line, or returns null if the line is not valid. */
    private User parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return null;
        }
        if (parts[2].equals("ADMIN")) {
            return new Admin(parts[0], parts[1]);
        }
        if (parts[2].equals("PLAYER")) {
            return new Player(parts[0], parts[1]);
        }
        return null;
    }
}