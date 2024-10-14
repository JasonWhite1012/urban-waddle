package components.ui;

import static components.dino.Dino.baseATK;
import static components.dino.Dino.ratio;
import interfaces.Drawable;
import java.awt.*;

public class DestroyCounter implements Drawable {
    private static final int COUNTER_MAX_ZEROS = 2;
    private static final int COUNTER_MAX_HIGH_COUNTER = 99999;

    public static int counter = 0;

    public DestroyCounter() {
    }

    private String scoreBuilder(int counter) {
        StringBuilder ret = new StringBuilder(Integer.toString(counter));
        char zero = '0';

        for (int i = 0; i < COUNTER_MAX_ZEROS - Integer.toString(counter).length(); i++) {
            ret.insert(0, zero);
        }

        return ret.toString();
    }

    public void addScore(int x) {
        counter += x;
    }

    private String printCrazy(int counter) {
        return counter > COUNTER_MAX_HIGH_COUNTER ? Integer.toString(COUNTER_MAX_HIGH_COUNTER)
                : scoreBuilder(10 - counter);
    }

    private String printATK(int counter) {
        return counter > COUNTER_MAX_HIGH_COUNTER ? Integer.toString(COUNTER_MAX_HIGH_COUNTER) : scoreBuilder(counter);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Consolas", Font.BOLD, 24));
        g.drawString("Crazy Points : ", 20, 320);
        g.drawString(printCrazy(counter), 220, 320);
        g.drawString("ATK : ", 20, 340);
        g.drawString(printATK((int) Math.ceil((baseATK * ratio))), 100, 340);
        g.setColor(Color.LIGHT_GRAY);
    }

    @Override
    public void reset() {
        counter = 0;
    }
}
