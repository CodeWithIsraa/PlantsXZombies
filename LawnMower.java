/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class LawnMower extends MainElements implements Movable {
    private boolean active;

    public LawnMower(int x, int y) {
        super(x, y);
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    @Override
    public void move() {
        if (active) {
            this.x++;
        }
    }
}
