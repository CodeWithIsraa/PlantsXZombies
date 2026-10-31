/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public abstract class MainElements {
    protected int x;
    protected int y;
    protected boolean active = true;
    
    public MainElements() {}

    public MainElements(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public boolean isActive() {
        return active;
    }
    public void disappear() {
        this.active = false;
    }
}
