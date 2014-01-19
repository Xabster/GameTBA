package platsnip.view;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import platsnip.model.State;
import platsnip.view.textures.TextureLoader;

import java.io.InputStream;
import java.net.URL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

public class OpenGLView implements View {
    private String WINDOW_TITLE = "Snipers are awesome!";

    private TextureLoader textureLoader;
    private int height = 720;
    private int width = 1280;

    @Override
    public void render(State state) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        Display.setTitle(WINDOW_TITLE + " (FPS: " + "XXXXXXXXXXX" + ")");

        // if escape has been pressed, stop the game
        if ((Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))) {
            System.exit(-1);
        }
        //Display.sync(60);
    }

    @Override
    public void initialize() {
        try {
            //Display.setFullscreen(true);
            Display.setDisplayMode(new DisplayMode(width, height));
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
            System.out.println("platsnip.Game exiting - exception in initialization:");
            le.printStackTrace();
            System.exit(-1);
        }
    }
}
