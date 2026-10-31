/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class Sun extends MainElements{
    private int value;

    public Sun(int x, int y) {
        super(x, y);
        this.value = 25; // Standard Sun value
    }

    public int getValue() {
        return value;
    }

    public void move() {
        this.y++;
    }
}
