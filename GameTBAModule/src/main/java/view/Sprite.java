package view;

import org.lwjgl.opengl.GL11;
import view.textures.Texture;
import view.textures.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Sprite {

    /**
     * The texture that stores the image for this sprite
     */
    private Texture texture;

    protected String ref;

    /**
     * Create a new sprite from a specified image.
     *
     * @param loader the texture loader to use
     * @param ref    A reference to the image on which this sprite should be based
     */
    public Sprite(TextureLoader loader, String ref) {
        this.ref = ref;
        try {
            texture = loader.getTexture(ref);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    public void draw(int x, int y, int width, int height, float angle) {
        // store the current model matrix
        glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        glTranslatef(x, y, 0);

        if (angle != 0) {
            glTranslatef(width/2, height/2, 0);
            glRotatef(angle, 0f, 0f, 1f);
            glTranslatef(-width/2, -height/2, 0);
        }

        // draw a quad textured to match the sprite
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(0, texture.getHeight());
            glVertex2f(0, height);

            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2f(width, height);

            glTexCoord2f(texture.getWidth(), 0);
            glVertex2f(width, 0);
        }
        glEnd();

        // restore the model view matrix to prevent contamination
        glPopMatrix();
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "texture=" + texture +
                ", ref='" + ref + '\'' +
                '}';
    }
}