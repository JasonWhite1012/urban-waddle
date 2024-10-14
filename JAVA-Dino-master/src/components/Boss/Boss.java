package components.Boss;

import static components.dino.Dino.baseATK;
import static components.dino.Dino.ratio;
import static components.dino.FireBall.fireBallList;
import static components.dino.FireBall.killFireball;
import components.dino.FireBallImage;
import static components.ground.Ground.GROUND_Y;
import components.utility.*;
import interfaces.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static main.GamePanel.debugMode;
import static main.GamePanel.posX;
import static main.GamePanel.posY;

public class Boss implements Drawable {

    private static final float Boss_START_X = 650;
    private static int Boss_LIFE_ANIMATION_DELTA_TIME = 60;
    private static final int Boss_BORDER_SIZE = 1;

    private static final float X = Boss_START_X;

    public static boolean isMario = false;
    public static boolean marioLoaded = false;

    private static BossStates bossState = BossStates.IDLE;

    private static BufferedImage idleImage = new Resource().getResourceImage("/assets/demon1.png");
    private static BufferedImage dieImage = new Resource().getResourceImage("/assets/demon_died.png");
    private static Animation lifeAnimation = new Animation(Boss_LIFE_ANIMATION_DELTA_TIME);

    private BossBlood bossblood = new BossBlood(50, 50);// Sean add
    private static final Sound attackSound = new Sound("/assets/sounds/Attack.wav");

    /**
     * Collision adjustments.
     * It is modified version from chromium source code.
     * ---------------------------------------------------
     * ______
     * _| |-|
     * | | Boss | |
     * |_| |_|
     * |_____ |
     */
    public static ArrayList<Coordinates> constructedCoordinates = new ArrayList<>();
    private static final Coordinates collisionMiddle = new Coordinates(650, GROUND_Y - idleImage.getHeight(),
            132 - Boss_BORDER_SIZE, 160 - Boss_BORDER_SIZE);
    private static float y = GROUND_Y - idleImage.getHeight();

    /**
     * This variable is for checking y before Boss hit the ground.
     * Without it Boss for msPerFrame ms is under the ground.
     */

    /**
     * It eliminates system delay between typed key event and pressed key event
     */
    public int chargeValue;
    public int ATKstrengh;
    public boolean jumpRequested;
    public boolean isBelow;

    public Sound gameOverSound = new Sound("/assets/sounds/hit.wav");

    public Boss() {
        lifeAnimation.addFrame(new Resource().getResourceImage("/assets/demon1.png"));
        lifeAnimation.addFrame(new Resource().getResourceImage("/assets/demon2.png"));

        constructedCoordinates.add(
                new Coordinates((int) X + collisionMiddle.x, (int) y, collisionMiddle.width, collisionMiddle.height));
    }

    public void setBlood(int n) {
        bossblood.setBlood(n);
    }

    public void live() {
        bossState = BossStates.LIVE;
    }

    public void die() {
        bossState = BossStates.DIE;
        gameOverSound.play();
    }

    public boolean isAlive() {
        switch (bossState) {
            case BossStates.LIVE:
                return true;
            default:
                return false;
        }
        // return bossState == BossStates.LIVE;
    }

    public boolean isFireballCollision() {
        for (FireBallImage fb : fireBallList) {
            if (fb.coordinates.intersects(collisionMiddle)) {
                if (!attackSound.isOpen())
                    attackSound.play();
                int pos = fireBallList.indexOf(fb);
                killFireball(pos);
                bossblood.subBlood((int) (baseATK * ratio));
                System.out.println("血量剩餘 : " + bossblood.getBlood());
                posX = (int) fb.getX() + 50;
                posY = (int) fb.getY();
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        if (debugMode) {
            for (Coordinates coordinates : constructedCoordinates) {
                g.setColor(Color.BLACK);
                g.drawRect(coordinates.x, coordinates.y, coordinates.width, coordinates.height);
            }
        }

        bossblood.draw(g);

        switch (bossState) {
            case IDLE:
                break;
            case LIVE:
                lifeAnimation.update();
                g.drawImage(lifeAnimation.getFrame(), (int) X, (int) y, null);
                break;
            case DIE:
                g.drawImage(dieImage, (int) X, (int) y - 30, null);
                break;
        }
    }

    @Override
    public void update() {
        // oqihfoa
    }

    @Override
    public void reset() {
        y = GROUND_Y - idleImage.getHeight();
        live();
        bossblood.setBlood(100);
    }

    public int nowblood() {
        return bossblood.getBlood();
    }

    /*
     * public void subBlood(int n)
     * {
     * for(int i = 0;i<n;++i)
     * bossblood.subBlood();
     * }
     * 
     * public void addBlood(int n)
     * {
     * for(int i = 0;i<n;++i)
     * bossblood.addBlood();
     * }
     */

    public float getX() {
        return X;
    }

    public float getY() {
        return y;
    }
}
