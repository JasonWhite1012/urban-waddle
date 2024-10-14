package components.dino;

import components.utility.Coordinates;
import components.utility.ComponentImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static components.ground.Ground.GROUND_Y;

public class FireBallImage {
    private final ComponentImage FireBall_IMAGE;
    private int spaceBehind;

    public Coordinates coordinates;

    public FireBallImage(BufferedImage FireBall_IMAGE) {
        this.FireBall_IMAGE = new ComponentImage(FireBall_IMAGE, 0, GROUND_Y - FireBall_IMAGE.getHeight(), Color.red);
        this.spaceBehind = 0;

        coordinates = new Coordinates(this.FireBall_IMAGE.x, this.FireBall_IMAGE.y, FireBall_IMAGE.getWidth(), FireBall_IMAGE.getHeight());
    }

    public FireBallImage(BufferedImage FireBall_IMAGE, int spaceBehind) {
        this.FireBall_IMAGE = new ComponentImage(FireBall_IMAGE, 0, GROUND_Y - FireBall_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.FireBall_IMAGE.x, this.FireBall_IMAGE.y, FireBall_IMAGE.getWidth(), FireBall_IMAGE.getHeight());
    }

    public FireBallImage(BufferedImage FireBall_IMAGE, int x, int spaceBehind ,int y) {
        this.FireBall_IMAGE = new ComponentImage(FireBall_IMAGE, x, y - FireBall_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.FireBall_IMAGE.x, this.FireBall_IMAGE.y, FireBall_IMAGE.getWidth(), FireBall_IMAGE.getHeight());
    }

    // Setters ---------------------------------------------------------------
    public void setSpaceBehind(int spaceBehind) {
        this.spaceBehind = spaceBehind;
    }

    public void setX(int x) {
        this.FireBall_IMAGE.x = x;
        coordinates.x = x;
    }

    // Getters ---------------------------------------------------------------
    public int getX() {
        return FireBall_IMAGE.x;
    }

    public int getY() {
        return FireBall_IMAGE.y;
    }

    public Color getDebugColor() {
        return FireBall_IMAGE.debugColor;
    }

    public BufferedImage getFireBall_IMAGE() {
        return FireBall_IMAGE.image;
    }

    public int getSpaceBehind() {
        return spaceBehind;
    }
}
