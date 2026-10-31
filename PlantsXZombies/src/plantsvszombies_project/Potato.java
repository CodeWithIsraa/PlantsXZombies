/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class Potato extends Plant {
    public Potato() {
        super(25, PlantType.POTATO, 0, 0, 1000);
    }

    public Potato(int x, int y) {
        super(25, PlantType.POTATO, x, y, 1000);
    }

    @Override
    public void action() {
        this.disappear();
    }
}
