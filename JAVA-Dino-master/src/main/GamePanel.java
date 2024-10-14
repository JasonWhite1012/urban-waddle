package main;

import components.Boss.Boss;
import components.Boss.BossBlood;
import components.Boss.BossCounter;
import components.Boss.BossHitEffect;
import components.Boss.Bossobstacles;
import components.Props.Props;
import components.attack.AttackObstacleEffect;
import components.background.Background;
import components.dino.Dino;
import components.dino.FireBall;
import components.ground.Ground;
import components.obstacles.Obstacles;
import components.ui.BossBar;
import components.ui.ChargeBar;
import components.ui.DestroyCounter;
import components.ui.FireballSizer;
import components.ui.GameOver;
import components.ui.Intro;
import components.ui.Paused;
import components.ui.Score;
import components.utility.GameFlow;
import components.utility.MusicPanel;
import components.utility.Sound;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import static main.GameWindow.WINDOW_HEIGHT;
import static main.GameWindow.WINDOW_WIDTH;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private static final int GAME_START_SPEED = 5;
    private static final int GAME_FPS = 60;
    private static final int GAME_MAX_SPEED = 12;

    // private static GameFlow GF = GameFlow.NEW ;
    private final MusicPanel musics = new MusicPanel();

    public static final float GAME_GRAVITY = 0.64f;

    private Thread mainThread = new Thread(this);

    public static boolean debugMode = false;
    public static int gameSpeed = GAME_START_SPEED;
    public static boolean isGameSpeedChanged = false;

    public boolean running = false;
    public boolean paused = false;
    public boolean gameOver = false;
    public boolean intro = true;
    public boolean BossStage = false;
    final Object pauseLock = new Object();

    Dino dino = new Dino();
    Ground ground = new Ground();
    Obstacles obstacles = new Obstacles();
    Background background = new Background();
    Props props = new Props();// sean add
    Boss boss = new Boss();
    Bossobstacles bossobstacles = new Bossobstacles();
    BossBlood bossBlood = new BossBlood();
    FireBall fireballs = new FireBall();
    Score scoreUI = new Score();
    GameOver gameOverUI = new GameOver();
    Paused pausedUI = new Paused();
    Intro introUI = new Intro();
    DestroyCounter destroyer = new DestroyCounter();
    BossCounter bossCounter = new BossCounter();

    ChargeBar chargeBar = new ChargeBar();
    BossBar bossBar = new BossBar();
    public int chargeValue;
    public int fullChargeAmount;
    public static final int maxFullChargeAmount = 3;
    public boolean isHoldingKeyK = false;
    ArrayList<FireballSizer> fireballSizer = new ArrayList<>();

    public static int posX, posY;
    private int atkTimes = 0;
    private AttackObstacleEffect attackEffect;
    private BossHitEffect bossHitEffect = new BossHitEffect((int) boss.getX(), (int) boss.getY(), 20, 5);

    private static final Sound COUNTER_SOUND = new Sound("/assets/sounds/PowerUp.wav");
    private static final Sound CHARGE_SOUND = new Sound("/assets/sounds/FullChargeNotice.wav");

    public GamePanel() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);
        setVisible(true);

        add(introUI.introLabel);
        add(chargeBar.getBar());
        add(bossBar.getBar());
        chargeValue = 0;
        chargeBar.setBounds((int) 40, (int) 180, 50, 5);
        bossBar.setBounds((int) boss.getX() - 10, 10, 100, 10);
        for (int i = 0; i < 3; i++) {
            fireballSizer.add(new FireballSizer());
            fireballSizer.get(i).setBounds(20 + i * 55, 270, 50, 20);
            add(fireballSizer.get(i).getBar());
        }

        mainThread.start();
        musics.setMusic(GameFlow.NEW);
    }

    public void startGame() {
        System.out.println("\nGame log");
        System.out.println("-----------------------------------------------------");

        running = true;
        intro = false;
        BossStage = false;
        musics.setMusic(GameFlow.IN);
        introUI.setVisible(false);
    }

    public void resetGame() {
        gameOver = false;
        running = true;
        BossStage = false;
        gameSpeed = GAME_START_SPEED;

        scoreUI.reset();
        boss.reset();
        dino.reset();
        obstacles.reset();
        props.reset();
        ground.reset();
        background.reset();
        bossobstacles.reset();
        fireballs.reset();
        destroyer.reset();
        chargeBar.reset();
        bossBar.reset();
        bossBar.getBar().setVisible(false);
        musics.setMusic(GameFlow.IN);
        fullChargeAmount = 0;

        for (FireballSizer fire : fireballSizer) {
            fire.reset();
        }

        dino.addBlood(3);
        atkTimes = 0;

        if (Dino.isMario) {
            // introUI.overworld.playInLoop();
            // It prevents from layering sounds
            if (dino.gameOverSound.isOpen())
                dino.gameOverSound.stop();
        }

        mainThread = new Thread(this);
        mainThread.start();
    }

    public void pauseGame() {
        paused = true;
        System.out.println("Paused");
    }

    public void resumeGame() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notify();
            System.out.println("Resumed");
        }
    }

    private void changeGameSpeed() {
        if (Score.score > 0 && Score.score % 100 == 0 && !isGameSpeedChanged && gameSpeed < GAME_MAX_SPEED) {
            isGameSpeedChanged = true;
            gameSpeed += 1;
        }
    }

    private void triggerEffect(int x, int y) {
        attackEffect = new AttackObstacleEffect(x, y - 20, 5);
    }

    /**
     * MAIN PAINT METHOD
     * --------------------------------------------------------
     * 
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        background.draw(g);

        if (paused)
            pausedUI.draw(g);
        if (gameOver)
            gameOverUI.draw(g);
        if (!intro)
            scoreUI.draw(g);

        ground.draw(g);
        dino.draw(g);
        chargeBar.draw(g);
        destroyer.draw(g);
        fireballs.draw(g);

        if (!BossStage) {
            obstacles.draw(g);
            props.draw(g);
        } else {
            boss.draw(g);
            bossCounter.draw(g);
            if (boss.isAlive() && bossHitEffect.isActive()) {
                bossHitEffect.draw(g);
            }
            bossobstacles.draw(g);
        }

        if (attackEffect != null) {
            attackEffect.draw(g);
        }

        if (intro)
            introUI.draw(g);
    }

    /**
     * MAIN GAME LOOP
     * It is probably the simplest version
     * ------------------------------------------------------------------------
     * Good resources:
     * -
     * https://gamedev.stackexchange.com/questions/160329/java-game-loop-efficiency
     * - https://stackoverflow.com/questions/18283199/java-main-game-loop
     */
    @Override
    public void run() {
        // INTRO LOOP FOR EASTER EGG
        while (intro) {
            try {
                int msPerFrame = 1000 / GAME_FPS;
                Thread.sleep(msPerFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Dino.isMario && !Dino.marioLoaded) {
                dino.setMario();
            }

            repaint();
        }

        // MAIN GAME LOOP
        while (running) {

            // GAME TIMING
            try {
                int msPerFrame = 1000 / GAME_FPS;
                Thread.sleep(msPerFrame);

                if (paused) {
                    synchronized (pauseLock) {
                        repaint();
                        pauseLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // GAME LOGIC
            changeGameSpeed();

            if (!BossStage) {
                props.update();
                obstacles.update();
            }

            scoreUI.update(); // wayne
            background.update();
            dino.update();
            chargeBar.update();
            ground.update();
            fireballs.update();
            bossBar.getBar().setValue(boss.nowblood());

            if (BossStage) {
                bossobstacles.update();
            }

            if (dino.isBelow) {
                chargeValue += 40;
                chargeBar.setChargeValue(chargeValue);
            }

            if (!chargeBar.isSpeak && chargeBar.getChargeValue() >= 1000) {
                chargeBar.isSpeak = true;
                System.out.println("Full Charge...!!!");
                if (!CHARGE_SOUND.isOpen())
                    CHARGE_SOUND.play();
                fullChargeAmount = Math.min(fullChargeAmount + 1, maxFullChargeAmount);
                fireballSizer.get(fullChargeAmount - 1).setFireballSizerTrue();
            }

            if (dino.isRighting && dino.getX() <= 400 && BossStage) {
                dino.setX(dino.getX() + 3);
            }

            if (dino.isLefting && dino.getX() >= 53 && BossStage) {
                dino.setX(dino.getX() - 3);
            }

            if (attackEffect != null) {
                attackEffect.update();
                if (!attackEffect.isActive()) {
                    attackEffect = null;
                }
            }

            if (bossHitEffect.isActive()) {
                bossHitEffect.update();
            }

            // If not in BossStage
            if (!BossStage) {
                if (obstacles.isFireballCollision()) {
                    System.out.println("kill obstacle");
                    triggerEffect(posX, posY);
                    ++atkTimes;
                    destroyer.addScore(1);
                }
            } else {
                if (boss.isFireballCollision()) {
                    System.out.println("ATK boss");
                    triggerEffect(posX, posY);
                    bossHitEffect.triggerEffect(16);
                }
                if (bossobstacles.isFireballCollision()) {
                    System.out.println("kill bossobstracle");
                    triggerEffect(posX, posY);
                    ++atkTimes;
                    destroyer.addScore(1);
                }
            }

            if (atkTimes == 10) {
                atkTimes = 0;
                dino.addBlood(1);
                if (!COUNTER_SOUND.isOpen())
                    COUNTER_SOUND.play();
                destroyer.reset();
            }

            /* Jason */
            if (props.isCollision()) {
                if (!COUNTER_SOUND.isOpen())
                    COUNTER_SOUND.play();
                if (dino.nowblood() >= 3)
                    Dino.ratio += 0.2;
                else
                    dino.addBlood(1);
            }

            int minusBlood = obstacles.isCollision();
            if (minusBlood > 0) {
                dino.subBlood(minusBlood);
            }

            if (bossobstacles.isCollision()) {
                dino.subBlood(1);
            }

            if (dino.nowblood() < 1) {
                gameOver = true;
                running = false;

                dino.die();
                // resetGame();
                // if (Dino.isMario)
                // introUI.overworld.stop();
                scoreUI.writeHighScore();
                System.out.println("Game over");
                musics.setMusic(GameFlow.NEW);
            }

            if (boss.nowblood() < 1) {
                gameOver = true;
                running = false;
                // intro = true;
                // BossStage = false;
                System.out.println("Boss blood : " + bossBlood.getBlood());
                System.out.println("Boss blood 2: " + boss.nowblood());

                musics.setMusic(GameFlow.WIN);

                boss.die();
                // boss.reset();
                // chargeBar.reset();
                // bossBar.reset();
                // if (Dino.isMario)
                // introUI.overworld.stop();
                // resetGame();
                scoreUI.writeHighScore();
                System.out.println("Game win!!");
            }

            if (Score.score == 300 && BossStage == false) {
                for (int i = 0; i < obstacles.getIncomingObstacles().size(); i++)
                    obstacles.clearObstacles(i);
                for (int i = 0; i < props.getIncomingProps().size(); i++)
                    props.clearProps();

                BossStage = true;
                musics.setMusic(GameFlow.BOSS);
                bossBar.setVisible();
                boss.live();
            }

            // RENDER OUTPUT
            repaint();
        }
    }

    /**
     * KEY BINDINGS
     *
     * -------------------------------------------
     * Debug mode: '`'
     * Jump: ' ', 'w', 'ARROW UP'
     * Fall: 's', 'ARROW DOWN'
     * Pause: 'p', 'ESC'
     * -------------------------------------------
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // DEBUG
        if (e.getKeyChar() == '`') {
            debugMode = !debugMode;
        }

        // move left
        if (e.getKeyChar() == 'a') {
            dino.isLefting = true;
        }

        // move right
        if (e.getKeyChar() == 'd') {
            dino.isRighting = true;
        }

        // JUMP
        if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            if (!paused && running) {
                dino.jump();
            } else if (paused && running) {
                resumeGame();
            }

            if (!running && !gameOver) {
                startGame();
                dino.run();
                dino.jump();
            } else if (gameOver) {
                System.out.println("Restart!");
                resetGame();
            }
        }

        // FALL && BELOW
        if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!paused && running) {

                if (dino.isInAir()) {
                    dino.fall();
                } else {
                    dino.isBelow = true;
                    dino.below();
                }
            }
        }

        // Fire!
        if (e.getKeyChar() == 'f') {
            if (!paused && running) {
                if (!isHoldingKeyK) {
                    isHoldingKeyK = true;
                    if (fullChargeAmount > 0) {
                        fireballs.generateFireBall((int) dino.getX(), (int) dino.getY());
                        fireballSizer.get(fullChargeAmount - 1).setFireballSizerFalse();
                        fullChargeAmount--;
                    }
                }
            }
        }

        // PAUSE
        if (e.getKeyChar() == 'p' || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!paused && running) {
                pauseGame();
            } else if (paused && running) {
                resumeGame();
            }
        }
    }

    /**
     * Just checking if someone change mind to jump
     * right after hitting ground
     * --------------------------------------------------------
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP)
            dino.jumpRequested = false;
        else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN)/* Jason */
        {
            chargeBar.isSpeak = false;

            if (dino.isBelow == true) {
                dino.isBelow = false;
                // if (chargeBar.isCharged()) {
                /*
                 * if (!chargeBar.isSpeak && chargeBar.getChargeValue() >= 1000) {
                 * System.out.println("Full Charge...!!!");
                 * if (!CHARGE_SOUND.isOpen())
                 * CHARGE_SOUND.play();
                 * }
                 */
                // fullChargeAmount = Math.min(fullChargeAmount + 1, maxFullChargeAmount);
                // fireballSizer.get(fullChargeAmount - 1).setFireballSizerTrue();
                // }
                dino.run();
            }
            chargeValue = 0;
            chargeBar.reset();
        } else if (e.getKeyChar() == 'a') {
            dino.isLefting = false;
        } else if (e.getKeyChar() == 'd') {
            dino.isRighting = false;
        } else if (e.getKeyChar() == 'f') {
            isHoldingKeyK = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
