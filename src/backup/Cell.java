/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 20176838
 */
public class Cell {

    private boolean isAlive = false;

    /*
    private int xpos = 0;
    private int ypos = 0;
    
    public Cell(boolean state, int xpos, int ypos){
        Cell cell = new Cell();
        cell.setIsAlive(true);
        cell.setXpos(xpos);
        cell.setYpos(ypos);
    }*/
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
        int width=20;
        int height=20;
        g.clearRect(x, y, width, height);
       
        g.drawRect(x, y, width, height);
        if (isAlive) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.black);
        }
    }

}
