package components.obstacles;

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

public class Obstacles implements Drawable {
    private static final int OBSTACLES_MIN_SPACE_BETWEEN = 250;
    private static final int OBSTACLES_MAX_SPACE_BETWEEN = 500;
    private static final int OBSTACLES_FIRST_OBSTACLE_X = 600;
    private static final int RANGE_SPACE_BETWEEN_OBSTACLES = OBSTACLES_MAX_SPACE_BETWEEN - OBSTACLES_MIN_SPACE_BETWEEN + 1;

    private static final ArrayList<ObstacleImage> OBSTACLE_IMAGES = new ArrayList<>();

    private static final Sound hitSound = new Sound("/assets/sounds/hit.wav");
    private static final Sound attackSound = new Sound("/assets/sounds/Attack.wav");

    private static final int MAX_INCOMING_OBSTACLES = 5;
    public static ArrayList<ObstacleImage> incomingObstacles;

    public Obstacles() {
        ObstacleImage cactus1 = new ObstacleImage(new Resource().getResourceImage("/assets/Cactus-1.png"));
        ObstacleImage cactus2 = new ObstacleImage(new Resource().getResourceImage("/assets/Cactus-2.png"));
        ObstacleImage cactusTriple = new ObstacleImage(new Resource().getResourceImage("/assets/Cactus-3.png"));
        ObstacleImage cactusDouble1 = new ObstacleImage(new Resource().getResourceImage("/assets/Cactus-4.png"));
        ObstacleImage cactusDouble2 = new ObstacleImage(new Resource().getResourceImage("/assets/Cactus-5.png"));
        ObstacleImage hole1 = new ObstacleImage(new Resource().getResourceImage("/assets/eight.png"));

        OBSTACLE_IMAGES.add(cactus1);
        OBSTACLE_IMAGES.add(cactus2);
        OBSTACLE_IMAGES.add(cactusTriple);
        OBSTACLE_IMAGES.add(cactusDouble1);
        OBSTACLE_IMAGES.add(cactusDouble2);
        OBSTACLE_IMAGES.add(hole1);

        initFirstObstacles();
    }

    private void initFirstObstacles() {
        incomingObstacles = new ArrayList<>();

        for (int i = 0; i < MAX_INCOMING_OBSTACLES; i++) {
            ObstacleImage rand = getRandomObstacle();

            incomingObstacles.add(rand);
            if (i == 0) {
                incomingObstacles.get(0).setX(OBSTACLES_FIRST_OBSTACLE_X, rand.ObstacleState);
            } else {
                incomingObstacles.get(i).setX(incomingObstacles.get(i - 1).getX() + incomingObstacles.get(i - 1).getSpaceBehind(), rand.ObstacleState);
            }
        }
    }

    private ObstacleImage getRandomObstacle() {
        int randCactus = (int) (Math.random() * (OBSTACLE_IMAGES.size()));
        ObstacleImage randObstacle = OBSTACLE_IMAGES.get(randCactus);

        if (randCactus == 5) {
            return new ObstacleImage(randObstacle.getOBSTACLE_IMAGE(), getRandomSpace(), ObstacleType.HOLE);
        } else {
            return new ObstacleImage(randObstacle.getOBSTACLE_IMAGE(), getRandomSpace());
        }
    }

    private ObstacleImage getRandomObstacle(int x) {
        int randCactus = (int) (Math.random() * (OBSTACLE_IMAGES.size()));
        ObstacleImage randObstacle = OBSTACLE_IMAGES.get(randCactus);

        if (randCactus == 5) {
            return new ObstacleImage(randObstacle.getOBSTACLE_IMAGE(), x, getRandomSpace(), ObstacleType.HOLE);
        } else {
            return new ObstacleImage(randObstacle.getOBSTACLE_IMAGE(), x, getRandomSpace());
        }
    }

    private int getRandomSpace() {
        return (int) (Math.random() * RANGE_SPACE_BETWEEN_OBSTACLES) + OBSTACLES_MIN_SPACE_BETWEEN;
    }
    /*
     * public boolean isCollision() {
     * for (ObstacleImage obstacle : incomingObstacles) {
     * for (Coordinates dinoCoordinates : Dino.constructedCoordinates)
     * if (dinoCoordinates.intersects(obstacle.coordinates)) {
     * return true;
     * }
     * }
     * return false;
     * }
     */

    public int isCollision() {
        for (ObstacleImage obstacle : incomingObstacles) {
            for (Coordinates dinoCoordinates : Dino.constructedCoordinates) {
                if (dinoCoordinates.intersects(obstacle.coordinates)) {
                    hitSound.play();
                    if (obstacle.ObstacleState == ObstacleType.HOLE) {
                        System.out.println("Run into Holes...");
                        return 3;
                    }
                    incomingObstacles.remove(obstacle);
                    ObstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
                    incomingObstacles.add(
                            getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
                    incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
                    return 1;
                }
            }
        }
        return 0;
    }

    public boolean isFireballCollision() {
        for (ObstacleImage obstacle : incomingObstacles) {
            if (obstacle.ObstacleState == ObstacleType.HOLE)
                continue;

            for (FireBallImage fb : fireBallList) {
                if (fb.coordinates.intersects(obstacle.coordinates)) {
                    if(!attackSound.isOpen()) attackSound.play();
                    incomingObstacles.remove(obstacle);
                    ObstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
                    incomingObstacles.add(
                            getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
                    incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
                    // System.out.println("Kill Obstracle !!!");
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

    public void clearObstacles(int mostFrontPos) /* Jason */
    {
        if (incomingObstacles.size() > 0) {
            incomingObstacles.remove(mostFrontPos);

            ObstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
            incomingObstacles
                    .add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
            incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
        }
    }

    public ArrayList<ObstacleImage> getIncomingObstacles()/* Jason */
    {
        return incomingObstacles;
    }

    @Override
    public void reset() {
        initFirstObstacles();
    }

    @Override
    public void update() {
        for (ObstacleImage obstacle : incomingObstacles) {
            obstacle.setX(obstacle.getX() - GamePanel.gameSpeed, obstacle.ObstacleState);
        }

        if (incomingObstacles.get(0).getX() < -incomingObstacles.get(0).getOBSTACLE_IMAGE().getWidth()) {
            ObstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);

            incomingObstacles.remove(0);
            incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
            incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
        }
    }

    @Override
    public void draw(Graphics g) {
        for (ObstacleImage obstacle : incomingObstacles) {
            if (GamePanel.debugMode) {
                g.setColor(obstacle.getDebugColor());
                g.drawRect(obstacle.coordinates.x, obstacle.coordinates.y, obstacle.coordinates.width,
                        obstacle.coordinates.height);
            }
            if (obstacle.ObstacleState == ObstacleType.GROUND)
                g.drawImage(obstacle.getOBSTACLE_IMAGE(), obstacle.getX(), obstacle.getY(), null);
            else
                g.drawImage(obstacle.getOBSTACLE_IMAGE(), obstacle.getX(), obstacle.getY() + 100, null);
        }
    }
}
