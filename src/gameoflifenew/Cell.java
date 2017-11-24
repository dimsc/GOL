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

    public void draw(Graphics g, int x, int y, boolean line) {

        int width = 20;
        int height = 20;
        int div = 20;
        int xpos;
        int ypos;
        if (line) {
            xpos = (x * div) + 10;
            ypos = (y * div) + 40;
            g.clearRect(xpos, ypos, width, height);
            g.drawRect(xpos, ypos, width, height);
            /*
            System.out.println("Cell xpos:"+xpos);
            System.out.println("Cell ypos:"+ypos);
            System.out.println("Cell width:"+width);
            System.out.println("Cell height:"+height);
            */
        } else {
            xpos = (x * div);
            ypos = (y * div);
            //System.out.println("Cell xpos:"+xpos);
            //System.out.println("Cell ypos:"+ypos);
            while (width > 0 && height > 0) {
                g.clearRect(xpos, ypos, width-1, height-1);
                g.drawRect(xpos, ypos, width-1, height-1);
                /*
                System.out.println("Cell while xpos:"+xpos);
                System.out.println("Cell while ypos:"+ypos);
                System.out.println("Cell while width:"+width);
                System.out.println("Cell while height:"+height);*/
                width = width - 1;
                height = height - 1;
            }
        }

        //g.drawLine(x+25, y+30, x+35, y+20);
        //g.setColor(Color.BLACK);
//        if (isAlive) {
//            g.setColor(Color.PINK);
//        } else {
//            g.setColor(Color.orange);
//        }
    }

}
