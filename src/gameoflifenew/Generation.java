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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;
import java.util.ArrayList;
import java.util.List;

public class Generation extends Frame implements Runnable {

    private final int xDim, yDim;
    private final int xCell, yCell;
    private final int div = 20;
    protected Cell[][] cell;
    private boolean line = false;
    private StartButton startButton;
    private EndButton endButton;
    private static Thread thread;
    private static boolean status = false;
    private List<Integer> xsave = new ArrayList<Integer>();
    private List<Integer> ysave = new ArrayList<Integer>();

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

        startButton = new StartButton(100, 680, "Start");
        this.add(startButton);
        startButton.setEnabled(true);

        endButton = new EndButton(300, 680, "End");
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

    public int getCellMapDimX() {
        return xCell;
    }

    public int getCellMapDimY() {
        return yCell;
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

        if ((nrActiveCells < 2) || (nrActiveCells > 3)) {
            curState = false;
        } else if (nrActiveCells == 3) {
            curState = true;
        }

        return curState;
    }

    public void evolve(List<Integer> xsave, List<Integer> ysave) {

        for (int p = 0; p < xsave.size(); p++) {
            int cellDimX = xsave.get(p);
            int cellDimY = ysave.get(p);
             System.out.println("cellDimX: " + cellDimX);
             System.out.println("cellDimY: " + cellDimY);
           

            //        int cellDimX = 10;
            //        int cellDimY = 10;
            // Get cells
            //boolean[][] cellMap = new boolean[cellDimX][cellDimY];
            Cell[][] cellMap = this.getCell();

            // Create dummy cells
            boolean[][] cellMapDummy = new boolean[cellDimX][cellDimY];

            // Read neighboring cell
            for (int i = 0; i < cellDimX; i++) {
                for (int j = 0; j < cellDimY; j++) {
                    int nrActiveCells = countActiveNeighboringCells(cellMap, cellDimX, cellDimY, i, j);
                    boolean nextState = nextCellState(nrActiveCells, cellMap[i][j].getState());

                    // Store next cell state in the dummy cell map
                    cellMapDummy[i][j] = nextState;
                }
            }

            // Change all cell states from the dummy cell map
            for (int i = 0; i < cellDimX; i++) {
                for (int j = 0; j < cellDimY; j++) {
                    cellMap[i][j].setState(cellMapDummy[i][j]);
                }
            }
            line = false;
            repaint();
            System.out.println("painted");
        }

    }

    private void showCellMap() {
        int cellDimX = this.getCellMapDimX();
        int cellDimY = this.getCellMapDimY();

//        int cellDimX = 10;
//        int cellDimY = 10;
        // Get cells
        Cell[][] cellMap = this.getCell();

        //System.out.println("Start evolution!!");
        System.out.println("\n");

        for (int j = (cellDimY - 1); j >= 0; j--) {
            String txt = "";

            for (int i = (cellDimX - 1); i >= 0; i--) {
                if (cellMap[i][j].getState() == true) {
                    txt += "*|";
                } else {
                    txt += " |";
                }
            }

            System.out.println(txt);
        }
    }

    private void setCellPattern(int pattern) {
        switch (pattern) {
            case 1:
                setPatternBlinker();
                break;
            case 2:
                setPatternToad();
                break;
            default:
                setPatternBlinker();
                break;
        }
    }

    private void setPatternToad() {
        int cellDimX = this.getCellMapDimX();
        int cellDimY = this.getCellMapDimY();

//        int cellDimX = 10;
//        int cellDimY = 10;
        // Get cells
        Cell[][] cellMap = this.getCell();

        for (int i = 0; i < cellDimX; i++) {
            for (int j = 0; j < cellDimY; j++) {
                boolean isActive = false;

                if (i == 1) {
                    if ((j == 2) || (j == 3)) {
                        isActive = true;
                    }
                } else if ((i == 2) && (j == 1)) {
                    isActive = true;
                } else if ((i == 3) && (j == 4)) {
                    isActive = true;
                } else if (i == 4) {
                    if ((j == 2) || (j == 3)) {
                        isActive = true;
                    }
                }

                cellMap[i][j].setState(isActive);
            }
        }

        showCellMap();
    }

    private void setPatternBlinker() {
        int cellDimX = this.getCellMapDimX();
        int cellDimY = this.getCellMapDimY();

//        int cellDimX = 10;
//        int cellDimY = 10;
        // Get cells
        Cell[][] cellMap = this.getCell();

        for (int i = 0; i < cellDimX; i++) {
            for (int j = 0; j < cellDimY; j++) {
                boolean isActive = false;

                if (i == 2) {
                    if ((j == 1) || (j == 2) || (j == 3)) {
                        isActive = true;
                    }
                }

                cellMap[i][j].setState(isActive);
            }
        }

        showCellMap();
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        //while (status) {
            System.out.println("Doing heavy processing - START " + thread.getName());
            try {
                thread.sleep(1000);
                
                evolve(xsave, ysave);
                System.out.println("xsave :" + xsave.size());
                
                //Get database connection, delete unused data from DB
                //doDBProcessing();
            } catch (InterruptedException e) {
                e.printStackTrace();
                status = false;
            }
            System.out.println("Doing heavy processing - END " + thread.getName());
        //}
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
                int xcoordinate = Math.round(evt.getX() / 20) * 20 + 10;
                int ycoordinate = Math.round(evt.getY() / 20) * 20;

                int xsaver = (evt.getX() - 10) / div;
                int ysaver = (evt.getY() - 40) / div;

                xsave.add(Integer.valueOf(xsaver));
                ysave.add(Integer.valueOf(ysaver));

                System.out.println("xcoordinate: " + xcoordinate);
                System.out.println("ycoordinate: " + ycoordinate);

                repaint();
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
               String text ="";
                if (this.cell[i][k].getState()) {
                    text ="black";
                    g.setColor(Color.black);
                } else {
                     text ="white";
                    g.setColor(Color.white);
                } 
                System.out.println("xCell [i : " +i+"][ yCell : " + k + "][ state : " + this.cell[i][k].getState() + "][ color : " + text + "]");
                this.cell[i][k].draw(g, i, k, line);
                //System.out.println("paint [i : " + i + "][ k : " + k + "][ state : " + this.cell[i][k].getState() + "]");
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

        String txt = "";
        Cell oneCell = this.cell[xcoordinate][ycoordinate];
        if (oneCell.getState()) {
            oneCell.setState(false);
            txt = "die";
        } else {
            oneCell.setState(true);
            txt = "alive";
        }
        this.cell[xcoordinate][ycoordinate] = oneCell;
        System.out.println("findCell [xcoordinate : "+xcoordinate +"][ ycoordinate : "+ycoordinate + "][ oneCell : "+txt  +"]");

    }

    private class StartButton extends Button {

        public StartButton(int x, int y, String symbol) {
            setLocation(x, y);
            setSize(30, 30);
            setBackground(Color.GREEN);
            setLabel(symbol);
            enableEvents(AWTEvent.ACTION_EVENT_MASK);
        }

        @Override
        protected void processActionEvent(ActionEvent e) {
            System.out.println("Start evolve");
            status = true;
            run();
        }

    }

    private class EndButton extends Button {

        public EndButton(int x, int y, String symbol) {
            setLocation(x, y);
            setSize(30, 30);
            setBackground(Color.ORANGE);
            setLabel(symbol);
            enableEvents(AWTEvent.ACTION_EVENT_MASK);
        }

        @Override
        protected void processActionEvent(ActionEvent e) {
            //System.out.println("End evolve");
            setCellPattern(2);
        }

    }
}
