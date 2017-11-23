package gameoflifenew;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class Generation extends Frame  {
    private final int xDim, yDim;
    private final int xCell, yCell;
    private final int div = 20;
    protected Cell[][] cell;
    
    protected Generation(int w, int h, String s) {
      super(s);  
      xDim= w; 
      yDim= h;  
      xCell = w / div;
      yCell = h / div;
      cell = new Cell[xCell][yCell];
      setUpMainWindow();
      setupMouseAdapter(); 
      this.setInitialConfiguration();
    } 
    
    private void setUpMainWindow() {
        setLayout(null);
        setSize(xDim,yDim); 
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }
    
    private void setupMouseAdapter() {
        
        addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed (MouseEvent evt){
                //this.cell = new Cell[xDim][yDim];
                for (int i = 0; i < xDim; i++) {
                    for (int k = 0; k < yDim; k++) {
                        if(evt.getX() == i && evt.getY() ==k){
                            System.out.println("X pos: " + evt.getX()); 
                            System.out.println("Y pos: " + evt.getY());
                        }
                    }
                }
            }
        }); 
    }
    @Override
    public void processWindowEvent(WindowEvent evt) {
        if(evt.getID()==WindowEvent.WINDOW_CLOSING) System.exit(1);
    }
   
    @Override
    public void paint(Graphics g){ 
        for (int i = 0; i < xCell; i++) {
            for (int k = 0; k < yCell; k++) {
                this.cell[i][k].draw(g, i, k);
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
} 