package gameoflifenew;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

public class Generation extends Frame {

    private final int xDim, yDim;
    private final int xCell, yCell;
    private final int div = 20;
    protected Cell[][] cell;
    private boolean line = false;
    private StartButton startButton;
    private EndButton endButton;

    protected Generation(int w, int h, String s) {
        super(s);
        xDim = w;
        yDim = h;
        xCell = w / div;
        yCell = h / div;
        cell = new Cell[xCell][yCell];
        System.out.println("xCell: " + xCell);
        System.out.println("yCell: " + yCell);
        setUpMainWindow();
        setupMouseAdapter();
        
        startButton= new StartButton(100, 680,"Start");
        this.add(startButton);
        startButton.setEnabled(true); 
        
        endButton= new EndButton(300,680,"End");
        this.add(endButton);
        endButton.setEnabled(true); 
        
        line = true;
        this.setInitialConfiguration();
    }

    public Cell[][] getCell() {
        return cell;
    }

    public void setCell(Cell[][] cell) {
        this.cell = cell;
    }
    
    private void setUpMainWindow() {
        setLayout(null);
        setSize(xDim, yDim);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    private int countActiveNeighboringCells(Cell[][] cell, int dimX, int dimY, int posX, int posY) {
        int activeCounter = 0;

        int indexX;
        int indexY;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }

                indexX = posX + i;
                indexY = posY + j;

                // Correcting x position
                if (indexX < 0) {
                    indexX = dimX - 1;
                } else if (indexX >= dimX) {
                    indexX = 0;
                }

                // Correcting y position
                if (indexY < 0) {
                    indexY = dimY - 1;
                } else if (indexY >= dimY) {
                    indexY = 0;
                }

                if (cell[indexX][indexY].getState() == true) {
                    activeCounter++;
                }
            }
        }

        return activeCounter;
    }

    private boolean nextCellState(int nrActiveCells, boolean curState) {

        if (nrActiveCells < 2) {
            curState = false;
        }
        return curState;
    }

    public void evolve() {
        int cellDimX = 10;
        int cellDimY = 10;

        // Get cells
        //boolean[][] cellMap = new boolean[cellDimX][cellDimY];
        Cell[][] cellMap = this.getCell();

        // Create dummy cells
        boolean[][] cellMapDummy = new boolean[cellDimX][cellDimY];

        // Read neighboring cell
        for (int i = 0; i < cellDimX; i++) {
            for (int j = 0; j < cellDimY; j++) {
                //cellMapDummy[cellDimX][cellDimY] = true;
                int nrActiveCells = countActiveNeighboringCells(cellMap, cellDimX, cellDimY, i, j);
                boolean nextState = nextCellState(nrActiveCells, cellMap[i][j].getState());

                cellMap[i][j].setState(nextState);
            }
        }
    }

    
    private void setupMouseAdapter() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //this.cell = new Cell[xDim][yDim];
                System.out.println("X pos: " + evt.getX());
                System.out.println("Y pos: " + evt.getY());
                findCell(evt.getX(), evt.getY());
                // 
                line = false;
                int xcoordinate = Math.round(evt.getX()/20)*20+10;
                int ycoordinate = Math.round(evt.getY()/20)*20; 
                System.out.println("xcoordinate: " + xcoordinate);
                System.out.println("ycoordinate: " + ycoordinate);
                
                repaint(xcoordinate, ycoordinate, 20, 20);
                //this.cell[i][k].draw(g, i, k);
            }
        });
    }

    @Override
    public void processWindowEvent(WindowEvent evt) {
        if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(1);
        }
    }

    @Override
    public void paint(Graphics g) {
        
        for (int i = 0; i < xCell; i++) {
            for (int k = 0; k < yCell; k++) {
                if (this.cell[i][k].getState()) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.black);
                }
                this.cell[i][k].draw(g, i, k, line);
            }
        }
    }

    private void setInitialConfiguration() {
        //this.cell = new Cell[xDim][yDim];
        for (int i = 0; i < xCell; i++) {
            for (int k = 0; k < yCell; k++) {
                setCellConfiguration(i, k, false);
            }
        }
    }

    public void setCellConfiguration(int xpos, int ypos, boolean state) {
        this.cell[xpos][ypos] = new Cell();
        this.cell[xpos][ypos].setState(state);
    }

    private void findCell(int xpos, int ypos) {

        int xcoordinate = (xpos - 10) / this.div;
        int ycoordinate = (ypos - 40) / this.div;

        //System.out.println("xcoordinate: " + xcoordinate);
        //System.out.println("ycoordinate: " + ycoordinate);

        Cell oneCell = this.cell[xcoordinate][ycoordinate];
        if (oneCell.getState()) {
            oneCell.setState(false);
        } else {
            oneCell.setState(true);
        }
    }
    


        private class StartButton extends Button{
            public StartButton(int x, int y, String symbol){
                setLocation(x,y);
                setSize(30,30);
                setBackground(Color.GREEN);
                setLabel(symbol);  
                enableEvents(AWTEvent.ACTION_EVENT_MASK);
            }

            @Override
            protected void processActionEvent(ActionEvent e) {
                
            }      
        }
        
         private class EndButton extends Button{
            public EndButton(int x, int y, String symbol){
                setLocation(x,y);
                setSize(30,30);
                setBackground(Color.ORANGE);
                setLabel(symbol);  
                enableEvents(AWTEvent.ACTION_EVENT_MASK);
            }

            @Override
            protected void processActionEvent(ActionEvent e) {
                
            }      
        }
}
