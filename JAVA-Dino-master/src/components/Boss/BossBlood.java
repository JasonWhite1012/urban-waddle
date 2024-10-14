package components.Boss;

import components.utility.Resource;
import interfaces.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BossBlood implements Drawable {
    public static int myblood;
    private final int maxBlood;
    private static final BufferedImage blood3 = new Resource().getResourceImage("/assets/blood3.png");
    private static final BufferedImage blood2 = new Resource().getResourceImage("/assets/blood2.png");
    private static final BufferedImage blood1 = new Resource().getResourceImage("/assets/blood1.png");
    private static final BufferedImage blood0 = new Resource().getResourceImage("/assets/blood0.png");

    private static final int INITIAL_X = 90;
    private static final int INITIAL_Y = 10;
    private int x;
    private int y;

    public BossBlood() {
        this(100, 100);
    }

    public BossBlood(int initialBlood, int maxBlood) {
        this.myblood = initialBlood;
        this.maxBlood = maxBlood;
        this.x = INITIAL_X;
        this.y = INITIAL_Y;
    }

    public void setBlood(int n) {
        myblood = n;
    }

    public void addBlood(int x) {
        for (int i = 0; i < x; ++i)
            if (myblood < maxBlood) {
                myblood++;
            }
    }

    public void subBlood(int x) {
        for (int i = 0; i < x; ++i)
            if (myblood > 0) {
                myblood--;
            }
    }

    @Override
    public void draw(Graphics g) {
        switch (myblood) {
            case 3:
                // g.drawImage(blood3, x, y, null);
                break;
            case 2:
                // g.drawImage(blood2, x, y, null);
                break;
            case 1:
                // g.drawImage(blood1, x, y, null);
                break;
            default:
                // g.drawImage(blood0, x, y, null);
                break;
        }
    }

    @Override
    public void update() {
        // EMPTY
    }

    public int getBlood() {
        return myblood;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void reset() {
        myblood = maxBlood;
    }
}
