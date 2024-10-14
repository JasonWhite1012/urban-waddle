package components.dino;

import components.utility.Resource;
import interfaces.Drawable;
import java.awt.*;
import java.util.ArrayList;
import main.GamePanel;

public class FireBall implements Drawable {

    private static final ArrayList<FireBallImage> FireBall_IMAGE = new ArrayList<>();
    //private static final Coordinates fireballCoordinate = new Coordinates(0, 15, 11 - ballWidth, 21 - ballWidth);

    private static final int MAX_INCOMING_OBSTACLES = 5;
    public static ArrayList<FireBallImage> fireBallList = new ArrayList<>();

    public FireBall() {
        FireBallImage cactus1 = new FireBallImage(new Resource().getResourceImage("/assets/attackEffect.png"));
        FireBall_IMAGE.add(cactus1);
        fireBallList = new ArrayList<>();
    }

    public void generateFireBall(int x,int y)
    {
        System.out.println("Summon a new FireBall !");
        FireBallImage newfb = FireBall_IMAGE.get(0);
        FireBallImage fireballImage = new FireBallImage(newfb.getFireBall_IMAGE(),x,y,y + 25);
        fireBallList.add(fireballImage);
        //System.out.println(fireBallList.size());
    }

    /*public boolean isCollision() {
        for (ObstacleImage obstacle : incomingObstacles)
        {
            if (fireballCoordinate.intersects(obstacle.coordinates))
            {
                incomingObstacles.remove(obstacle);
                ObstacleImage lastIncomingObstacle = incomingObstacles.get(incomingObstacles.size() - 1);
                incomingObstacles.add(getRandomObstacle(lastIncomingObstacle.getX() + lastIncomingObstacle.getSpaceBehind()));
                incomingObstacles.get(incomingObstacles.size() - 1).setSpaceBehind(getRandomSpace());
                System.out.println("Kill Obstracle !!!");
                return true;
            }
        }
        return false;
    }*/

    public static void killFireball(int pos)
    {
        fireBallList.remove(pos);
    }

    @Override
    public void reset() {
        fireBallList = new ArrayList<>();
    }

    @Override
    public void update() {
        for (FireBallImage fireBall : fireBallList) {
            fireBall.setX(fireBall.getX() + 10); 
        }
        if(fireBallList.size() > 0)
            if (fireBallList.get(0).getX() > 800)
            {
                killFireball(0);
            }
    }

    @Override
    public void draw(Graphics g) {
        for (FireBallImage fireball : fireBallList) {
            if (GamePanel.debugMode) {
                g.setColor(fireball.getDebugColor());
                g.drawRect(fireball.coordinates.x, fireball.coordinates.y, fireball.coordinates.width, fireball.coordinates.height);
            }
            g.drawImage(fireball.getFireBall_IMAGE(), fireball.getX(), fireball.getY(), null);
        }
    }
}
