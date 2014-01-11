import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.entities.Entity;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import view.Sprite;
import view.textures.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Game {

    private String WINDOW_TITLE = "GAME TBA!";
    private TextureLoader textureLoader;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> removeList = new ArrayList<>();

    private long lastLoopTime = getTime();
    private long lastFpsTime;

    private int fps;
    private static long timerTicksPerSecond = Sys.getTimerResolution();

    public static boolean gameRunning = true;

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
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();

            // grab the mouse, dont want that hideous cursor when we're playing!
            //if (isApplication) {
            //    Mouse.setGrabbed(true);
            //}

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
            System.out.println("Game exiting - exception in initialization:");
            le.printStackTrace();
            Game.gameRunning = false;
            return;
        }

        startGame();
    }

    private void startGame() {
        // clear out any existing entities and initialise a new set
        entities.clear();
        initEntities();
    }

    private void initEntities() {

        int blocksize = 20;

        try (BufferedReader br = new BufferedReader(new FileReader("GameTBAModule/test.map"))) {
            int row = 0;
            String in;
            while ((in = br.readLine()) != null) {
                row++;
                int col = 0;
                for (byte b : in.getBytes()) {
                    col++;
                    switch (b) {
                        case '#': { // stone
                            entities.add(new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("stone.gif")));
                            break;
                        }
                        case ' ': { // air

                            break;
                        }
                        case 'w': {
                            entities.add(new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("water.gif")));
                            break;
                        }
                        case '?': { // sky
                            Entity e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("sky.gif"));
                            e.setRotation(90f);
                            entities.add(e);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        while (Game.gameRunning) {
            // clear screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            // let subsystem paint
            frameRendering();

            // update window contents
            Display.update();
        }

        Display.destroy();
    }

    public void frameRendering() {
        Display.sync(60);

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
            entity.draw();
            entity.update(delta);
        }

        // remove any entity that has been marked for clear up
        entities.removeAll(removeList);
        removeList.clear();

        // if escape has been pressed, stop the game
        if ((Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))) {
            Game.gameRunning = false;
        }
    }

    public static void main(String argv[]) {
        new Game().gameLoop();
    }

    public Sprite getSprite(String ref) {
        return new Sprite(textureLoader, ref);
    }
}