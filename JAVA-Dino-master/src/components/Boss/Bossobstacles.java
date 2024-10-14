package components.Boss;

import components.dino.Dino;
import static components.dino.FireBall.fireBallList;
import static components.dino.FireBall.killFireball;
import components.dino.FireBallImage;
import components.utility.Coordinates;
import components.utility.Resource;
import components.utility.Sound;
import interfaces.Drawable;
import java.awt.*;
import java.util.ArrayList;
import main.GamePanel;
import static main.GamePanel.posX;
import static main.GamePanel.posY;

public class Bossobstacles implements Drawable {
    private static final int OBSTACLES_MIN_SPACE_BETWEEN = 250;
    private static final int OBSTACLES_MAX_SPACE_BETWEEN = 400;
    private static final int OBSTACLES_FIRST_OBSTACLE_X = 600;
    private static final int Base_Y = 75;
    private static final int RANGE_SPACE_BETWEEN_OBSTACLES = OBSTACLES_MAX_SPACE_BETWEEN - OBSTACLES_MIN_SPACE_BETWEEN + 1;

    private static final ArrayList<BossobstacleImage> OBSTACLE_IMAGES = new ArrayList<>();

    private static final int MAX_INCOMING_OBSTACLES = 5;
    private ArrayList<BossobstacleImage> incomingObstacles;

    private static final Sound attackSound = new Sound("/assets/sounds/Attack.wav");


    public Bossobstacles() {
        BossobstacleImage BALL1 = new BossobstacleImage(new Resource().getResourceImage("/assets/bossatk.png"));
        BossobstacleImage BALL2 = new BossobstacleImage(new Resource().getResourceImage("/assets/bossATK2.png"));

        OBSTACLE_IMAGES.add(BALL1);
        OBSTACLE_IMAGES.add(BALL2);

        initFirstObstacles();
    }

    private void initFirstObstacles() {
        incomingObstacles = new ArrayList<>();

        for (int i = 0; i < MAX_INCOMING_OBSTACLES; i++) {
            BossobstacleImage rand = getRandomObstacle();

            incomingObstacles.add(rand);
            if (i == 0) {
                incomingObstacles.get(0).setX(OBSTACLES_FIRST_OBSTACLE_X);
                incomingObstacles.get(0).setY(Base_Y);
            } else {
                incomingObstacles.get(i).setX(incomingObstacles.get(i - 1).getX() + incomingObstacles.get(i - 1).getSpaceBehind());
                incomingObstacles.get(i).setY((int)(Math.random()*150)+Base_Y);
            }
        }
    }

    private BossobstacleImage getRandomObstacle() {
        int randCactus = (int) (Math.random() * (OBSTACLE_IMAGES.size()));
        BossobstacleImage randObstacle = OBSTACLE_IMAGES.get(randCactus);

        return new BossobstacleImage(randObstacle.getOBSTACLE_IMAGE(), getRandomSpace());
    }

    private BossobstacleImage getRandomObstacle(int x) {
        int randCactus = (int) (Math.random() * (OBSTACLE_IMAGES.size()));
        BossobstacleImage randObstacle = OBSTACLE_IMAGES.get(randCactus);

        return new BossobstacleImage(randObstacle.getOBSTACLE_IMAGE(), x, getRandomSpace());
    }

    private int getRandomSpace() {
        return (int) (Math.random() * RANGE_SPACE_BETWEEN_OBSTACLES) + OBSTACLES_MIN_SPACE_BETWEEN;
    }
    
    /* public boolean isCollision() {
        for (BossobstacleImage obstacle : incomingObstacles){
            for (Coordinates dinoCoordinates : Dino.constructedCoordinates) {
                if (dinoCoordinates.intersects(obstacle.coordinates)) {
                    incomingObstacles.remove(obstacle);
                    BossobstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
                    incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
                    //incomingObstacles.get(incomingObstacles.size() - 1).setY((int)(Math.random()*150)+75);
                    return true;
                }
            }
        }
        return false;
    } */

    public boolean isCollision() {
        for (BossobstacleImage obstacle : incomingObstacles){
            for (Coordinates dinoCoordinates : Dino.constructedCoordinates) {
                if (dinoCoordinates.intersects(obstacle.coordinates)) {
                    if(incomingObstacles.size() > 1 )
                    {
                        BossobstacleImage lastincomingObstacles = incomingObstacles.get(incomingObstacles.size() - 1);
                        incomingObstacles.remove(obstacle);
                        incomingObstacles.add(getRandomObstacle(lastincomingObstacles.getX() + lastincomingObstacles.getSpaceBehind()));
                        incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
                        incomingObstacles.get(incomingObstacles.size() - 1).setY((int)(Math.random()*150)+Base_Y);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isFireballCollision() {
        for (BossobstacleImage obstacle : incomingObstacles)
        {
            for(FireBallImage fb : fireBallList)
            {
                if (fb.coordinates.intersects(obstacle.coordinates))
                {
                    if(!attackSound.isOpen()) attackSound.play();
                    incomingObstacles.remove(obstacle);
                    BossobstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
                    incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
                    incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
                    incomingObstacles.get(incomingObstacles.size() - 1).setY((int)(Math.random()*150)+Base_Y);
                    int pos = fireBallList.indexOf(fb);
                    killFireball(pos);

                    posX = obstacle.getX();
                    posY = obstacle.getY();
                    return true;
                }
            }
        }
        return false;
    }

    public void clearObstacles(int mostFrontPos) /*Jason*/
    {
        if(incomingObstacles.size() > 0 )
        {
            incomingObstacles.remove(mostFrontPos);

            BossobstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
            incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
            incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
            incomingObstacles.get(incomingObstacles.size() - 1).setY((int)(Math.random()*150)+Base_Y);
        }
    }

    public ArrayList<BossobstacleImage> getIncomingObstacles()/*Jason*/
    {
        return incomingObstacles;
    }

    @Override
    public void reset() {
        initFirstObstacles();
    }

    @Override
    public void update() {
        for (BossobstacleImage obstacle : incomingObstacles) {
            obstacle.setX(obstacle.getX() - GamePanel.gameSpeed);
        }

        if (incomingObstacles.get(0).getX() < -incomingObstacles.get(0).getOBSTACLE_IMAGE().getWidth()) {
            BossobstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);

            incomingObstacles.remove(0);
            incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
            incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
        }
    }

    @Override
    public void draw(Graphics g) {
        for (BossobstacleImage obstacle : incomingObstacles) {
            if (GamePanel.debugMode) {
                g.setColor(obstacle.getDebugColor());
                g.drawRect(obstacle.coordinates.x, obstacle.coordinates.y, obstacle.coordinates.width, obstacle.coordinates.height);
            }
            g.drawImage(obstacle.getOBSTACLE_IMAGE(), obstacle.getX(), obstacle.getY(), null);
        }
    }
}
