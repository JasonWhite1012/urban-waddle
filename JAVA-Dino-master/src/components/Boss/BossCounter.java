package components.Boss;

import static components.Boss.BossBlood.myblood;
import interfaces.Drawable;
import java.awt.*;
import static main.GameWindow.WINDOW_WIDTH;

public class BossCounter implements Drawable {
    private static final int COUNTER_MAX_ZEROS = 2;
    private static final int COUNTER_MAX_HIGH_COUNTER = 99999;

    //public static int counter = 0;

    public BossCounter() { }

    private String scoreBuilder(int counter) {
        StringBuilder ret = new StringBuilder(Integer.toString(counter));
        char zero = '0';

        for (int i = 0; i < COUNTER_MAX_ZEROS - Integer.toString(counter).length(); i++) {
            ret.insert(0, zero);
        }

        return ret.toString();
    }

    public void addScore(int x)
    {
        //counter += x;
    }
    
    private String printHealth(int counter) {
        return counter > COUNTER_MAX_HIGH_COUNTER ? Integer.toString(COUNTER_MAX_HIGH_COUNTER) : scoreBuilder(counter);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString(printHealth((int)(myblood)), WINDOW_WIDTH-55, 20);
        g.setColor(Color.LIGHT_GRAY);
    }

    @Override
    public void reset() {
        //counter = 0;
    }
}
