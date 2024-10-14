package components.dino;

import components.utility.Resource;
import interfaces.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DinoBlood implements Drawable {
    private int myblood;
    private final int maxBlood;
    private static final BufferedImage blood3 = new Resource().getResourceImage("/assets/blood3.png");
    private static final BufferedImage blood2 = new Resource().getResourceImage("/assets/blood2.png");
    private static final BufferedImage blood1 = new Resource().getResourceImage("/assets/blood1.png");
    private static final BufferedImage blood0 = new Resource().getResourceImage("/assets/blood0.png");

    private static final int INITIAL_X = 40;
    private static final int INITIAL_Y = 20;
    private int x;
    private int y;

    public DinoBlood() {
        this(3, 3);
    }

    public DinoBlood(int initialBlood, int maxBlood) {
        this.myblood = initialBlood;
        this.maxBlood = maxBlood;
        this.x = INITIAL_X;
        this.y = INITIAL_Y;
    }

    public void addBlood() {
        if (myblood < maxBlood) {
            myblood++;
        }
    }

    public void subBlood() {
        if (myblood > 0) {
            myblood--;
        }
    }

    @Override
    public void draw(Graphics g) {
        switch (myblood) {
            case 3:
                g.drawImage(blood3, x, y, null);
                break;
            case 2:
                g.drawImage(blood2, x, y, null);
                break;
            case 1:
                g.drawImage(blood1, x, y, null);
                break;
            default:
                g.drawImage(blood0, x, y, null);
                break;
        }
    }

    @Override
    public void update() {
        // 占位符方法，未來可用於邏輯更新
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
