package components.ui;

import static components.dino.Dino.X;
import static components.dino.Dino.y;
import components.utility.DeltaTime;
import interfaces.Drawable;
import java.awt.*;
import javax.swing.JProgressBar;
import static main.GamePanel.gameSpeed;

public class ChargeBar implements Drawable {
    private static final int SCORE_MAX_ZEROS = 5;
    private static final int SCORE_DELTA_TIME = 100 / gameSpeed * 5;
    private static final int SCORE_MAX_HIGH_SCORE = 99999;
    private static int barWidth = 50;
    private static int barHeight = 5;

    private static final DeltaTime DELTA_TIME = new DeltaTime(SCORE_DELTA_TIME);

    private static boolean isPlayed = false;
    private static boolean FullCharge = false;

    private static int chargeValue = 0;

    private JProgressBar bar = new JProgressBar();

    public static boolean isSpeak = false;

    public ChargeBar() {
        bar = new JProgressBar(0, 1000);
        bar.setOrientation(JProgressBar.HORIZONTAL);
        bar.setForeground(Color.green);
        bar.setValue(0);
    }

    public JProgressBar getBar() {
        return bar;
    }

    public void setChargeValue(int value) {
        if (!FullCharge && chargeValue >= 1000) {
            FullCharge = true;
            return;
        }

        chargeValue = value;
        bar.setValue(chargeValue);
    }

    public int getChargeValue() {
        return chargeValue;
    }
    /*
     * private String scoreBuilder(int score) {
     * StringBuilder ret = new StringBuilder(Integer.toString(score));
     * char zero = '0';
     * 
     * for (int i = 0; i < SCORE_MAX_ZEROS - Integer.toString(score).length(); i++)
     * {
     * ret.insert(0, zero);
     * }
     * 
     * return ret.toString();
     * }
     * 
     * private String printCHARGE(int score) {
     * return score > SCORE_MAX_HIGH_SCORE ? Integer.toString(SCORE_MAX_HIGH_SCORE)
     * : scoreBuilder(score);
     * }
     */

    public void setBounds(int x, int y, int width, int height) {
        bar.setBounds(x, y, width, height);
    }

    public boolean isCharged() {
        return FullCharge;
    }

    @Override
    public void update() {
        bar.setBounds((int) X, (int) y - 18, barWidth, barHeight);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void reset() {
        chargeValue = 0;
        FullCharge = false;
        isSpeak = false;
        bar.setValue(0);
    }
}
