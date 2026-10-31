/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

import java.util.ArrayList;

public class Peashooter extends Plant {
    private ArrayList<Pea> peas;

    public Peashooter() {
        super(100, PlantType.PEASHOOTER, 0, 0, 300);
        this.peas = new ArrayList<>();
    }

    public Peashooter(int x, int y) {
        super(100, PlantType.PEASHOOTER, x, y, 300);
        this.peas = new ArrayList<>();
    }

    @Override
    public void action() {
        peas.add(new Pea(this.x, this.y, 20));
    }

    public ArrayList<Pea> getPeas() {
        return peas;
    }
}
