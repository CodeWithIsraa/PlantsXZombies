/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public class Cherry extends Plant{
    private int blastRadius;

    public Cherry() {
        super(150, PlantType.CHERRY, 0, 0, 150);
        this.blastRadius = 3;
    }

    public Cherry(int x, int y) {
        super(150, PlantType.CHERRY, x, y, 150);
        this.blastRadius = 3;
    }

    @Override
    public void action() {
        this.disappear();
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }
}
