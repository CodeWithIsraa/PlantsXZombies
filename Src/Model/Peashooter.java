/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class Pea extends MainElements{
    private int damage;

    public Pea(int x, int y, int damage) {
        super(x, y);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void moveForward() {
        this.x++;
    }

    public void hit() {
        disappear();
    }
}
