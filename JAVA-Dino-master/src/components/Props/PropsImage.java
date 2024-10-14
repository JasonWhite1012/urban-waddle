package components.Props;



import components.utility.Coordinates;
import components.utility.ComponentImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static components.ground.Ground.GROUND_Y;

public class PropsImage {
    private final ComponentImage Props_IMAGE;
    private int spaceBehind;

    public Coordinates coordinates;

    public PropsImage(BufferedImage Props_IMAGE) {
        this.Props_IMAGE = new ComponentImage(Props_IMAGE, 0, GROUND_Y - Props_IMAGE.getHeight(), Color.red);
        this.spaceBehind = 0;

        coordinates = new Coordinates(this.Props_IMAGE.x, this.Props_IMAGE.y, Props_IMAGE.getWidth(), Props_IMAGE.getHeight());
    }

    public PropsImage(BufferedImage Props_IMAGE, int spaceBehind) {
        this.Props_IMAGE = new ComponentImage(Props_IMAGE, 0, GROUND_Y - Props_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.Props_IMAGE.x, this.Props_IMAGE.y, Props_IMAGE.getWidth(), Props_IMAGE.getHeight());
    }

    public PropsImage(BufferedImage Props_IMAGE, int x, int spaceBehind) {
        this.Props_IMAGE = new ComponentImage(Props_IMAGE, x, GROUND_Y - Props_IMAGE.getHeight(), Color.red);
        this.spaceBehind = spaceBehind;

        coordinates = new Coordinates(this.Props_IMAGE.x, this.Props_IMAGE.y, Props_IMAGE.getWidth(), Props_IMAGE.getHeight());
    }

    // Setters ---------------------------------------------------------------
    public void setSpaceBehind(int spaceBehind) {
        this.spaceBehind = spaceBehind;
    }

    public void setX(int x) {
        this.Props_IMAGE.x = x;
        coordinates.x = x;
    }

    public void setY(int y) {
        this.Props_IMAGE.y = y;
        coordinates.y = y;
    }

    // Getters ---------------------------------------------------------------
    public int getX() {
        return Props_IMAGE.x;
    }

    public int getY() {
        return Props_IMAGE.y;
    }

    public Color getDebugColor() {
        return Props_IMAGE.debugColor;
    }

    public BufferedImage getProps_IMAGE() {
        return Props_IMAGE.image;
    }

    public int getSpaceBehind() {
        return spaceBehind;
    }
}
