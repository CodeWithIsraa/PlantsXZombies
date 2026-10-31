/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plantsvszombies_project;

public abstract class Zombie extends Character implements Movable, Attacker {
    protected int attackPower;
    protected int speed;
    protected ZombieType type;

    public Zombie() {
        super();
    }

    public Zombie(int attackPower, int speed, ZombieType type, int x, int y, int health) {
        super(x, y, health);
        this.attackPower = attackPower;
        this.speed = speed;
        this.type = type;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ZombieType getType() {
        return type;
    }

    @Override
    public void move() {
        this.x -= speed;
    }

    @Override
    public void action() {
        move();
    }
}
