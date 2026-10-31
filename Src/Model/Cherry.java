/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public abstract class Character extends MainElements{
    protected int health;
    protected double waitingTime;

    public Character() {
        super();
    }

    public Character(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            disappear();
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}
