package components.ui;

import interfaces.Drawable;
import java.awt.*;
import javax.swing.JProgressBar;
import static main.GamePanel.gameSpeed;

public class BossBar implements Drawable {
    private static final int SCORE_DELTA_TIME = 100 / gameSpeed ;

    private static int chargeValue = 0;

    private JProgressBar bar = new JProgressBar();

    public BossBar() 
    { 
        bar = new JProgressBar( 0, 100);
        bar.setOrientation(JProgressBar.HORIZONTAL);
        bar.setForeground(new Color(0, 0, 0));
        bar.setValue(100);
        bar.setVisible(false);
    }

    public JProgressBar getBar()
    {
        return bar;
    }

    public void setVisible()
    {
        bar.setVisible(true);
    }

    public void setBossBarValue(int value)
    {
        chargeValue = value;
        bar.setValue(chargeValue);
    }

    public int getChargeValue()
    {
        return chargeValue;
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
        chargeValue = 0;
        bar.setValue(0);
    }
}