package components.dino;

import static components.ground.Ground.GROUND_Y;
import components.ui.ChargeBar;
import components.utility.*;
import interfaces.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static main.GamePanel.GAME_GRAVITY;
import static main.GamePanel.debugMode;

public class Dino implements Drawable {
    private static final int DINO_JUMP_STRENGTH = 12;
    private static final int DINO_FALL_STRENGTH = 8;
    private static final float DINO_START_X = 50;
    private static final int DINO_BORDER_SIZE = 1;

    private static int DINO_RUNNING_ANIMATION_DELTA_TIME = 60;
    private static int DINO_BELOWING_ANIMATION_DELTA_TIME = 60;//

    public static double ratio = 1.0;
    public static double baseATK = 10;

    public static float X = DINO_START_X;

    public static boolean isMario = false;
    public static boolean marioLoaded = false;

    private static DinoStates dinoState = DinoStates.IDLE;

    private static BufferedImage idleImage = new Resource().getResourceImage("/assets/Dino-stand.png");
    private static BufferedImage jumpImage = new Resource().getResourceImage("/assets/Dino-stand.png");
    private static BufferedImage fallImage = new Resource().getResourceImage("/assets/Dino-stand.png");
    private static BufferedImage dieImage = new Resource().getResourceImage("/assets/Dino-big-eyes.png");

    private static Animation runAnimation = new Animation(DINO_RUNNING_ANIMATION_DELTA_TIME);
    private static Animation belowAnimation = new Animation(DINO_BELOWING_ANIMATION_DELTA_TIME);

    private DinoBlood dinoblood = new DinoBlood(3, 3);// Sean add

    /**
     * Collision adjustments.
     * It is modified version from chromium source code.
     * ---------------------------------------------------
     * ______
     * _| |-|
     * | | dino | |
     * |_| |_|
     * |_____ |
     */
    public static ArrayList<Coordinates> constructedCoordinates = new ArrayList<>();
    private static final Coordinates collisionLeft = new Coordinates(0, 15, 11 - DINO_BORDER_SIZE,
            21 - DINO_BORDER_SIZE);
    private static final Coordinates collisionMiddle = new Coordinates(10, 0, 22 - DINO_BORDER_SIZE,
            45 - DINO_BORDER_SIZE);
    private static final Coordinates collisionRight = new Coordinates(31, 0, 10 - DINO_BORDER_SIZE,
            21 - DINO_BORDER_SIZE);

    public static float y = GROUND_Y - idleImage.getHeight();
    private static float speedY;

    /**
     * This variable is for checking y before dino hit the ground.
     * Without it dino for msPerFrame ms is under the ground.
     */
    private static float TEMP_y;

    /**
     * It eliminates system delay between typed key event and pressed key event
     */
    public boolean jumpRequested;
    public boolean isBelow;
    public boolean isRighting;
    public boolean isLefting;
    public int chargeValue;
    ChargeBar chargeBar;
    public int ATKstrengh;

    private static Sound jumpSound = new Sound("/assets/sounds/button-press.wav");
    private static Sound attackSound = new Sound("/assets/sounds/Attack.wav");
    public Sound gameOverSound = new Sound("/assets/sounds/hit.wav");

    public Dino() {
        runAnimation.addFrame(new Resource().getResourceImage("/assets/Dino-left-up.png"));
        runAnimation.addFrame(new Resource().getResourceImage("/assets/Dino-right-up.png"));
        belowAnimation.addFrame(new Resource().getResourceImage("/assets/Dino-below-right-up.png"));
        belowAnimation.addFrame(new Resource().getResourceImage("/assets/Dino-below-left-up.png"));

        constructedCoordinates.add(new Coordinates((int) X, (int) y + collisionLeft.y, collisionLeft.width, collisionLeft.height));
        constructedCoordinates.add(new Coordinates((int) X + collisionMiddle.x, (int) y, collisionMiddle.width, collisionMiddle.height));
        constructedCoordinates.add(new Coordinates((int) X + collisionRight.x, (int) y, collisionRight.width, collisionRight.height));

        chargeBar = new ChargeBar();
    }

    public void run() {
        dinoState = DinoStates.RUNNING;
    }

    public void jump() {
        if (dinoState == DinoStates.RUNNING) {
            dinoState = DinoStates.JUMPING;

            speedY = -DINO_JUMP_STRENGTH;
            y += speedY;

            // It prevents from layering sounds and game freeze
            if (!jumpSound.isNull()) {
                if (jumpSound.isOpen())
                    jumpSound.stop();
            }
            jumpSound.play();

        } else if (isInAir()) {
            jumpRequested = true;
        }
    }

    public void below() {
        dinoState = DinoStates.BELOW;
    }

    public void runFaster() {
        isRighting = true;
    }

    public void runSlower() {
        isLefting = true;
    }

    public void fall() {
        if (isInAir()) {
            speedY = DINO_FALL_STRENGTH;
            y += speedY;
        }
    }

    public void die() {
        dinoState = DinoStates.DIE;
        gameOverSound.play();
    }

    public boolean isInAir() {
        return dinoState == DinoStates.JUMPING || dinoState == DinoStates.FALL;
    }

    public void setMario() {
        System.out.println("\nMARIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        DINO_RUNNING_ANIMATION_DELTA_TIME = 100;

        idleImage = new Resource().getResourceImage("/assets/mario/Mario-welcome.png");
        jumpImage = new Resource().getResourceImage("/assets/mario/Mario-jump.png");
        fallImage = new Resource().getResourceImage("/assets/mario/Mario-fall.png");
        runAnimation = new Animation(DINO_RUNNING_ANIMATION_DELTA_TIME);
        runAnimation.addFrame(new Resource().getResourceImage("/assets/mario/Mario-left-up.png"));
        runAnimation.addFrame(new Resource().getResourceImage("/assets/mario/Mario-right-up.png"));
        dieImage = new Resource().getResourceImage("/assets/mario/Mario-dead.png");

        jumpSound = new Sound("/assets/sounds/mario/jump.wav");
        gameOverSound = new Sound("/assets/sounds/mario/dead.wav");

        constructedCoordinates = new ArrayList<>();
        constructedCoordinates.add(new Coordinates((int) X, (int) y, idleImage.getWidth(), idleImage.getHeight()));

        marioLoaded = true;
        System.out.println("MARIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    }

    @Override
    public void draw(Graphics g) {
        if (debugMode) {
            for (Coordinates coordinates : constructedCoordinates) {
                g.setColor(Color.BLACK);
                g.drawRect(coordinates.x, coordinates.y, coordinates.width, coordinates.height);
            }
        }

        dinoblood.draw(g);

        switch (dinoState) {
            case IDLE:
                g.drawImage(idleImage, (int) X, (int) y, null);
                break;
            case JUMPING:
                g.drawImage(jumpImage, (int) X, (int) y, null);
                break;
            case FALL:
                g.drawImage(fallImage, (int) X, (int) y, null);
                break;
            case RUNNING:
                runAnimation.update();
                g.drawImage(runAnimation.getFrame(), (int) X, (int) y, null);
                break;
            case DIE:
                g.drawImage(dieImage, (int) X, (int) y, null);
                break;
            case BELOW:
                belowAnimation.update();
                g.drawImage(belowAnimation.getFrame(), (int) X, (int) y + 20, null);
                break;
        }
    }

    @Override
    public void update() {
        if ((TEMP_y + speedY) >= GROUND_Y - idleImage.getHeight()) {
            speedY = 0;
            y = GROUND_Y - idleImage.getHeight();
            run();
            if (jumpRequested) {
                jump();
                jumpRequested = false;
            }
        } else if (isInAir()) {
            speedY += GAME_GRAVITY;
            y += speedY;
            TEMP_y = y;

            if (speedY > 0)
                dinoState = DinoStates.FALL;
        }
        if (constructedCoordinates.size() > 1) {
            constructedCoordinates.get(0).x = (int) X;
            constructedCoordinates.get(0).y = (int) y + collisionLeft.y;

            if(dinoState == DinoStates.BELOW) {
                constructedCoordinates.get(1).x = (int) X + collisionMiddle.x;
                constructedCoordinates.get(1).y = (int) y+20;
                constructedCoordinates.get(1).height = collisionMiddle.height-20;

                constructedCoordinates.get(2).x = (int) X + collisionRight.x;
                constructedCoordinates.get(2).y = (int) y+20;
                constructedCoordinates.get(2).width = collisionRight.width + 15;
            }
            else {
                constructedCoordinates.get(1).x = (int) X + collisionMiddle.x;
                constructedCoordinates.get(1).y = (int) y;
                constructedCoordinates.get(1).height = collisionMiddle.height;

                constructedCoordinates.get(2).x = (int) X + collisionRight.x;
                constructedCoordinates.get(2).y = (int) y;
                constructedCoordinates.get(2).width = collisionRight.width ;
            }


        } else {
            constructedCoordinates.get(0).x = (int) X;
            constructedCoordinates.get(0).y = (int) y;
        }
        dinoblood.update();
    }

    @Override
    public void reset() {
        y = GROUND_Y - idleImage.getHeight();
        X = DINO_START_X;
        ratio = 1.0;
        run();
    }

    public void chargeAttack() {
        System.out.println("charge ATK !!!");
        attackSound.play();
    }

    public int nowblood() {
        return dinoblood.getBlood();
    }

    public void subBlood(int n) {
        for (int i = 0; i < n; ++i)
            dinoblood.subBlood();
    }

    public void addBlood(int n) {
        for (int i = 0; i < n; ++i)
            dinoblood.addBlood();
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        X = x;
    }

    public double getATK() {
        return baseATK;
    }

    public double getRatio() {
        return ratio;
    }
}
