package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.entities.Entity;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import view.Sprite;
import view.textures.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Game {
    public static float GRAVITY = 500;

    private String WINDOW_TITLE = "GAME TBA!";
    private TextureLoader textureLoader;
    private ArrayList<Entity> entities = new ArrayList<>();

    protected Entity player;

    private long lastLoopTime = getTime();
    private long lastFpsTime;

    private int fps;
    private static long timerTicksPerSecond = Sys.getTimerResolution();

    protected boolean gameRunning = true;

    public Game() {
        initialize();
    }

    public static long getTime() {
        return (Sys.getTime() * 1000) / timerTicksPerSecond;
    }

    public void initialize() {
        // initialize the window beforehand
        try {
            int height = 720;
            int width = 1280;
            Display.setTitle(WINDOW_TITLE);
            Display.setFullscreen(true);
            //Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();

            // enable textures since we're going to use these for our sprites
            glEnable(GL_TEXTURE_2D);
            // disable the OpenGL depth test since we're rendering 2D graphics
            glDisable(GL_DEPTH_TEST);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            glOrtho(0, width, height, 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glViewport(0, 0, width, height);

            textureLoader = new TextureLoader();
        } catch (LWJGLException le) {
            System.out.println("model.Game exiting - exception in initialization:");
            le.printStackTrace();
            gameRunning = false;
            return;
        }

        newGame();
    }

    private void newGame() {
        // clear out any existing entities and initialise a new set
        entities.clear();
        initEntities();
    }

    private void initEntities() {

        int blocksize = 8;

        try (BufferedReader br = new BufferedReader(new FileReader("GameTBAModule/test.map"))) {
            Entity e;
            int row = 0;
            String in;
            while ((in = br.readLine()) != null) {
                row++;
                int col = 0;
                for (byte b : in.getBytes()) {
                    col++;
                    switch (b) {
                        case '#': { // stone
                            e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("stone.gif"));
                            e.setMoveable(false);
                            entities.add(e);
                            break;
                        }
                        case 'P': { // player
                            System.out.println(col * blocksize + " " + row * blocksize + " " + blocksize * 2 + " " + blocksize * 4);
                            player = new Entity(col * blocksize, row * blocksize, blocksize * 2, blocksize * 4, getSprite("player.gif"));
                            player.setMoveable(true);
                            entities.add(player);
                            break;
                        }
                        case 'w': { //water
                            e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("water.gif"));
                            e.setMoveable(false);
                            entities.add(e);
                            break;
                        }
                        case '?': { // sky
                            e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("sky.gif"));
                            e.setMoveable(false);
                            entities.add(e);
                            break;
                        }
                        case ' ': { //air
                            break;
                        }
                        default: { // hmm unparseable
                            System.out.println("Unparseable: " + b);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pollInput() {
        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Mouse.getY();
            //System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            // jump
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    player.setVelocityX(-100);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    player.setVelocityX(100);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    //player.jump();
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    player.setVelocityX(0);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    player.setVelocityX(0);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    // no care?
                }
            }
        }
    }

    private void gameLoop() {
        while (gameRunning) {
            // clear screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            pollInput();

            doLogic();

            // let subsystem paint
            frameRendering();

            // update window contents
            Display.update();
        }

        Display.destroy();
    }

    private void doLogic() {
        for (Entity thisEntity : entities) {
            if (thisEntity.isMoveable()) {
                for (Entity otherEntity : entities) {
                    if (thisEntity != otherEntity) {
                        if (thisEntity.collidesWith(otherEntity)) {
                            // check if this is slower than what it's colliding with, but right now do it simple
                            thisEntity.setVelocityY(0);
                        }
                    }
                }
            }
        }
    }

    public void frameRendering() {
        // work out how long its been since the last update, this
        // will be used to calculate how far the entities should
        // move this loop
        long delta = getTime() - lastLoopTime;
        lastLoopTime = getTime();
        lastFpsTime += delta;
        fps++;

        // update our FPS counter if a second has passed
        if (lastFpsTime >= 1000) {
            Display.setTitle(WINDOW_TITLE + " (FPS: " + fps + ")");
            lastFpsTime = 0;
            fps = 0;
        }

        // cycle round drawing all the entities we have in the game
        for (Entity entity : entities) {
            entity.update(delta);
            entity.draw();
        }

        // if escape has been pressed, stop the game
        if ((Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))) {
            gameRunning = false;
        }

        //Display.sync(60);
    }

    public static void main(String argv[]) {
        new Game().gameLoop();
    }

    public Sprite getSprite(String ref) {
        return new Sprite(textureLoader, ref);
    }
}