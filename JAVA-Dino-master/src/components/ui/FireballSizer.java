package components.ui;

import interfaces.Drawable;
import java.awt.*;
import javax.swing.JProgressBar;

public class FireballSizer implements Drawable {        
    private static boolean isFull = true;

    private JProgressBar bar = new JProgressBar();

    public FireballSizer() 
    { 
        bar = new JProgressBar( 0, 1);
        bar.setOrientation(JProgressBar.VERTICAL);
        bar.setForeground(Color.ORANGE);
        bar.setValue(0);
    }

    public JProgressBar getBar()
    {
        return bar;
    }

    public void setFireballSizerTrue()
    {
        isFull = true;
        bar.setValue(1);
    }

    public void setFireballSizerFalse()
    {
        isFull = false;
        bar.setValue(0);
    }

    public boolean isCharged()
    {   
        return isFull;
    }

    public void setBounds(int x, int y, int width, int height) {
        bar.setBounds(x, y, width, height);
    }

    @Override
    public void update() {
       
    }

    @Override
    public void draw(Graphics g) {
        
    }

    @Override
    public void reset() {
        isFull = false;
        bar.setValue(0);
    }
}
