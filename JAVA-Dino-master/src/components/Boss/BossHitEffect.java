package components.Boss;

import components.utility.Resource;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BossHitEffect {
    private int x, y;
    private int duration;
    private int offset;
    private boolean active;

    private static BufferedImage BossGetHitImage = new Resource().getResourceImage("/assets/demonbeatk.png");
    private static BufferedImage BossNotHitImage = new Resource().getResourceImage("/assets/demon1.png");

    public int getX() {
        return x;
    }

    public int getWidth() {
        return x + 50;
    }

    public BossHitEffect(int x, int y, int duration, int offset) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.offset = offset;
        this.active = false;
    }

    public void update() {
        if (duration > 0) {
            duration--;
        } else {
            active = false;
        }
    }

    public void draw(Graphics g) {
        if (active) {
            if (duration > 3 * offset)
                g.drawImage(BossGetHitImage, x, y, null);
            else if (duration > 2 * offset)
                g.drawImage(BossNotHitImage, x, y, null);
            else if (duration > offset)
                g.drawImage(BossGetHitImage, x, y, null);
            else if (duration > 0)
                g.drawImage(BossNotHitImage, x, y, null);
        }
    }

    public void triggerEffect(int d) {
        active = true;
        duration = d;
    }

    public boolean isActive() {
        return active;
    }
}
