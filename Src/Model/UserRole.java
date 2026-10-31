/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class SunFlower extends Plant {
    private double sunTimer;

    public SunFlower() {
        super(50, PlantType.SUNFLOWER, 0, 0, 300);
    }

    public SunFlower(int x, int y) {
        super(50, PlantType.SUNFLOWER, x, y, 300);
    }

    public Sun produceSun() {
        return new Sun(this.x, this.y);
    }

    @Override
    public void action() {
        produceSun();
    }
}
