/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflifenew;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 20176838
 */
public class Cell {

    private boolean isAlive = false;

    public boolean getState() {
        return this.isIsAlive();
    }

    public void setState(boolean state) {
        setIsAlive(state);
    }

    private boolean isIsAlive() {
        return isAlive;
    }

    private void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void draw(Graphics g, int x, int y) {
        int width = 20;
        int height = 20;
        int div = 20;
        int xpos = (x * div) + 10;
        int ypos = (y * div) + 40;
        g.clearRect(xpos, ypos, width, height);
        g.drawRect(xpos, ypos, width, height);
        
        //g.drawLine(x+25, y+30, x+35, y+20);
        g.setColor(Color.BLACK);
        
//        if (isAlive) {
//            g.setColor(Color.PINK);
//        } else {
//            g.setColor(Color.orange);
//        }
    }

}
