/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public abstract class Plant extends Character implements Attacker {
    protected int cost;
    protected PlantType type;

    public Plant() {
        super();
    }

    public Plant(int cost, PlantType type, int x, int y, int health) {
        super(x, y, health);
        this.cost = cost;
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public PlantType getType() {
        return type;
    }
}
