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
public class Generation {

    private static final int xDim = 10;
    private static final int yDim = 10;
    private Cell[][] cell = new Cell[xDim][yDim];

    private static Generation instance;
    
    public Generation(){
        this.setInitialConfiguration();
    }

    public static Generation getInstance() {
        if (instance == null) {
            instance = new Generation();
        }
        return instance;
    }

    private void setInitialConfiguration() {
        //this.cell = new Cell[xDim][yDim];
        for (int i = 0; i < xDim; i++) {
            for (int k = 0; k < yDim; k++) {
                setCellConfiguration(i, k, false);
            }
        }
    }

    public void evolve() {
        // dife Rules
    }

    public void setCellConfiguration(int xpos, int ypos, boolean state) {
        this.cell[xpos][ypos] = new Cell();
        this.cell[xpos][ypos].setState(state);
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
    }

}
