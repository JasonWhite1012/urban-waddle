package components.ui;

import components.utility.Resource;
import interfaces.Drawable;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import static main.GameWindow.WINDOW_HEIGHT;
import static main.GameWindow.WINDOW_WIDTH;

public class Intro implements Drawable {
    private static BufferedImage text = new Resource().getResourceImage("/assets/Intro.png");

    public JLabel introLabel = new JLabel();

    public Intro() {

        introLabel.setBounds((WINDOW_WIDTH - text.getWidth()) / 2, (WINDOW_HEIGHT - text.getHeight()) / 2 - 50, text.getWidth(), text.getHeight());
        /*
        introLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //changeIntroTextToMario();
                overworld.playInLoop();
                //Dino.isMario = true;
            }
        }); 
        */
    }

    public void setVisible(boolean val) {
        introLabel.setVisible(val);
    }

    public void changeIntroTextToMario() {text = new Resource().getResourceImage("/assets/Intro-mario.png");}

    @Override
    public void draw(Graphics g) {
        g.drawImage(text, (WINDOW_WIDTH - text.getWidth())/ 2, (WINDOW_HEIGHT - text.getHeight()) / 2 - 50, null);
    }

    @Override
    public void update() { }

    @Override
    public void reset() { }
}
