package components.attack;

import components.utility.Resource;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AttackObstacleEffect {
    private int x, y;
    private int duration;
    private boolean active;

    private static BufferedImage attackEffectImage = new Resource().getResourceImage("/assets/fireball_explosion.png");

    public int getX()
    {
        return x;
    }
    
    public int getWidth()
    {
        return x + 50;
    }

    public AttackObstacleEffect(int x, int y, int duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.active = true;
    }

    public void update() {
        //x = x + 10;
        
        if (duration > 0) {
            duration--;
        } else {
            active = false;
        }
    }

    public void draw(Graphics g) {
        if (active) {
            g.drawImage(attackEffectImage, x, y, null);
        }
    }

    public boolean isActive() {
        return active;
    }
}
