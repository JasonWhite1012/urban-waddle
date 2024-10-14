package components.Props;

import components.dino.Dino;
import components.utility.Coordinates;
import components.utility.Resource;
import components.utility.Sound;
import interfaces.Drawable;
import java.awt.*;
import java.util.ArrayList;
import main.GamePanel;

public class Props implements Drawable {
    private static final int Props_MIN_SPACE_BETWEEN = 2000;
    private static final int Props_MAX_SPACE_BETWEEN = 5000;
    private static final int Props_FIRST_Props_X = 600;
    private static final int Props_Y = 75;
    private static final int RANGE_SPACE_BETWEEN_Props = Props_MAX_SPACE_BETWEEN - Props_MIN_SPACE_BETWEEN + 1;
    private static Sound eatSound = new Sound("/assets/sounds/mario/jump.wav");

    private static final ArrayList<PropsImage> Props_IMAGES = new ArrayList<>();

    private static final int MAX_INCOMING_Props = 5;
    private ArrayList<PropsImage> incomingProps;

    public Props() {
        PropsImage cactus1 = new PropsImage(new Resource().getResourceImage("/assets/apple.png"));
        /* PropsImage cactus2 = new PropsImage(new Resource().getResourceImage("/assets/Cactus-2.png"));
        PropsImage cactusTriple = new PropsImage(new Resource().getResourceImage("/assets/Cactus-3.png"));
        PropsImage cactusDouble1 = new PropsImage(new Resource().getResourceImage("/assets/Cactus-4.png"));
        PropsImage cactusDouble2 = new PropsImage(new Resource().getResourceImage("/assets/Cactus-5.png")); */

        Props_IMAGES.add(cactus1);
        /* Props_IMAGES.add(cactus2);
        Props_IMAGES.add(cactusTriple);
        Props_IMAGES.add(cactusDouble1);
        Props_IMAGES.add(cactusDouble2); */

        initFirstProps();
    }

    private void initFirstProps() {
        incomingProps = new ArrayList<>();

        for (int i = 0; i < MAX_INCOMING_Props; i++) {
            PropsImage rand = getRandomProps();

            incomingProps.add(rand);
            if (i == 0) {
                incomingProps.get(0).setX(Props_FIRST_Props_X);
                incomingProps.get(0).setY(Props_Y);
            } else {
                incomingProps.get(i).setX(incomingProps.get(i - 1).getX() + incomingProps.get(i - 1).getSpaceBehind());
                incomingProps.get(i).setY((int)(Math.random()*150)+75);
                //Math.random(Props_Y,200);
            }
        }
    }

    private PropsImage getRandomProps() {
        int randCactus = (int) (Math.random() * (Props_IMAGES.size()));
        PropsImage randProps = Props_IMAGES.get(randCactus);

        return new PropsImage(randProps.getProps_IMAGE(), getRandomSpace());
    }

    private PropsImage getRandomProps(int x) {
        int randCactus = (int) (Math.random() * (Props_IMAGES.size()));
        PropsImage randProps = Props_IMAGES.get(randCactus);

        return new PropsImage(randProps.getProps_IMAGE(), x, getRandomSpace());
    }

    private int getRandomSpace() {
        return (int) (Math.random() * RANGE_SPACE_BETWEEN_Props) + Props_MIN_SPACE_BETWEEN;

        
    }
 /*
    public boolean isCollision() {
        for (PropsImage Props : incomingProps) {
            for (Coordinates dinoCoordinates : Dino.constructedCoordinates)
                if (dinoCoordinates.intersects(Props.coordinates)) {
                    return true;
                }
        }
        return false;
    }
*/
    
    public boolean isCollision() {
        for (PropsImage Props : incomingProps){
            for (Coordinates dinoCoordinates : Dino.constructedCoordinates) {
                if (dinoCoordinates.intersects(Props.coordinates)) {
                    if(incomingProps.size() > 1 )
                    {
                        PropsImage lastIncomingProps = incomingProps.get(incomingProps.size() - 1);
                        incomingProps.remove(Props);
                        
                        eatSound.play();
                        incomingProps.add(getRandomProps(lastIncomingProps.getX() + lastIncomingProps.getSpaceBehind()));
                        incomingProps.get(incomingProps.size() - 1).setSpaceBehind(getRandomSpace());
                        incomingProps.get(incomingProps.size() - 1).setY((int)(Math.random()*150)+75);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void clearProps() 
    {
        if(incomingProps.size() > 1 )
        {
            
            incomingProps.remove(0);

            PropsImage lastIncomingProps = incomingProps.get(incomingProps.size() - 1);
            incomingProps.add(getRandomProps(lastIncomingProps.getX() + lastIncomingProps.getSpaceBehind()));
            incomingProps.get(incomingProps.size() - 1).setSpaceBehind(getRandomSpace());
            incomingProps.get(incomingProps.size() - 1).setY((int)(Math.random()*150)+75);
        }
    } 

    public ArrayList<PropsImage> getIncomingProps()/*Jason*/
    {
        return incomingProps;
    }

    @Override
    public void reset() {
        initFirstProps();
    }

    @Override
    public void update() {
        for (PropsImage Props : incomingProps) {
            Props.setX(Props.getX() - GamePanel.gameSpeed);
        }

        if (incomingProps.get(0).getX() < -incomingProps.get(0).getProps_IMAGE().getWidth()) {
            PropsImage lastIncomingProps = incomingProps.get(incomingProps.size() - 1);

            incomingProps.remove(0);
            incomingProps.add(getRandomProps(lastIncomingProps.getX() + lastIncomingProps.getSpaceBehind()));
            incomingProps.get(incomingProps.size() - 1).setSpaceBehind(getRandomSpace());
            incomingProps.get(incomingProps.size() - 1).setY((int)(Math.random()*150)+75);
        }
    }

    @Override
    public void draw(Graphics g) {
        for (PropsImage Props : incomingProps) {
            if (GamePanel.debugMode) {
                g.setColor(Props.getDebugColor());
                g.drawRect(Props.coordinates.x, Props.coordinates.y, Props.coordinates.width, Props.coordinates.height);
            }
            g.drawImage(Props.getProps_IMAGE(), Props.getX(), Props.getY(), null);
        }
    }
}

