package components.obstacles;

import static components.ground.Ground.GROUND_Y;
import components.utility.ComponentImage;
import components.utility.Coordinates;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ObstacleImage {
    private final ComponentImage OBSTACLE_IMAGE;
    private int spaceBehind;

    public ObstacleType ObstacleState = ObstacleType.GROUND;
    public Coordinates coordinates;

    public ObstacleImage(BufferedImage OBSTACLE_IMAGE) {
        this.OBSTACLE_IMAGE = new ComponentImage(OBSTACLE_IMAGE, 0, GROUND_Y - OBSTACLE_IMAGE.getHeight(), Color.red);
        this.spaceBehind = 0;

        coordinates = new Coordinates(this.OBSTACLE_IMAGE.x, this.OBSTACLE_IMAGE.y, OBSTACLE_IMAGE.getWidth(),
                OBSTACLE_IMAGE.getHeight());
    }

    public ObstacleImage(BufferedImage OBSTACLE_IMAGE, int spaceBehind) {
        this.OBSTACLE_IMAGE = new ComponentImage(OBSTACLE_IMAGE, 0, GROUND_Y - OBSTACLE_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.OBSTACLE_IMAGE.x, this.OBSTACLE_IMAGE.y, OBSTACLE_IMAGE.getWidth(),
                OBSTACLE_IMAGE.getHeight());
    }

    public ObstacleImage(BufferedImage OBSTACLE_IMAGE, int spaceBehind, ObstacleType type) {
        this.OBSTACLE_IMAGE = new ComponentImage(OBSTACLE_IMAGE, 0, GROUND_Y - OBSTACLE_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;
        this.setType(type);
        System.out.println("Is a hole...");
        coordinates = new Coordinates(this.OBSTACLE_IMAGE.x, this.OBSTACLE_IMAGE.y + 108,
                OBSTACLE_IMAGE.getWidth() - 20, OBSTACLE_IMAGE.getHeight());
    }

    public ObstacleImage(BufferedImage OBSTACLE_IMAGE, int x, int spaceBehind) {
        this.OBSTACLE_IMAGE = new ComponentImage(OBSTACLE_IMAGE, x, GROUND_Y - OBSTACLE_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.OBSTACLE_IMAGE.x, this.OBSTACLE_IMAGE.y, OBSTACLE_IMAGE.getWidth(),
                OBSTACLE_IMAGE.getHeight());
    }

    public ObstacleImage(BufferedImage OBSTACLE_IMAGE, int x, int spaceBehind, ObstacleType type) {
        this.OBSTACLE_IMAGE = new ComponentImage(OBSTACLE_IMAGE, x+20, GROUND_Y - OBSTACLE_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;
        this.setType(type);
        System.out.println("Is a hole...");
        coordinates = new Coordinates(this.OBSTACLE_IMAGE.x, this.OBSTACLE_IMAGE.y + 108,
                OBSTACLE_IMAGE.getWidth() - 20, OBSTACLE_IMAGE.getHeight());
    }

    // Setters ---------------------------------------------------------------
    public void setSpaceBehind(int spaceBehind) {
        this.spaceBehind = spaceBehind;
    }

    public void setX(int x, ObstacleType type) {
        if(type == ObstacleType.HOLE){
            this.OBSTACLE_IMAGE.x = x;
            coordinates.x = x+10;
        }
        else{
            this.OBSTACLE_IMAGE.x = x;
            coordinates.x = x;
        }
    }

    public void setType(ObstacleType TYPE) {
        ObstacleState = TYPE;
    }

    // Getters ---------------------------------------------------------------
    public int getX() {
        return OBSTACLE_IMAGE.x;
    }

    public int getY() {
        return OBSTACLE_IMAGE.y;
    }

    public Color getDebugColor() {
        return OBSTACLE_IMAGE.debugColor;
    }

    public BufferedImage getOBSTACLE_IMAGE() {
        return OBSTACLE_IMAGE.image;
    }

    public int getSpaceBehind() {
        return spaceBehind;
    }
}
