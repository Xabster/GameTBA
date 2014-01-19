package platsnip.model;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import platsnip.model.entities.Entity;
import platsnip.view.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameModel {
    public State integrate(State currentState, long time, long deltaTime) {

        for (Entity entity : currentState.listEntities()) {
            entity.update(deltaTime);
            entity.draw();
        }

        for (Entity thisEntity : currentState.listEntities()) {
            if (thisEntity.isMoveable()) {
                for (Entity otherEntity : currentState.listEntities()) {
                    if (thisEntity != otherEntity) {
                        if (thisEntity.collidesWith(otherEntity)) {

                            thisEntity.hasCollidedWith(otherEntity);
                            otherEntity.hasCollidedWith(thisEntity);

                            // check if this is slower than what it's colliding with, but right now do it simple
                            thisEntity.setVelocityY(0);
                        }
                    }
                }
            }
        }

        pollInput(currentState);
        // process inputs

        // apply inputs/physics/logic

        return null;
    }

    public State createNew() {
        return null;
    }

    public void pollInput(State currentState) {
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
                    currentState.getPlayer().setVelocityX(-100);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    currentState.getPlayer().setVelocityX(100);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    currentState.getPlayer().setVelocityY(-150);
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    currentState.getPlayer().setVelocityX(0);
                } else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    currentState.getPlayer().setVelocityX(0);
                }
            }
        }
    }
    private void loadMapToState(String mapFile, State state) {

        int blocksize = 16;

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
                            state.listEntities().add(e);
                            break;
                        }
                        case 'P': { // player
                            Entity player = new Entity(col * blocksize, row * blocksize, blocksize * 2, blocksize * 4, getSprite("player.gif"));
                            player.setAccelerationY(State.GRAVITY);
                            player.setMoveable(true);
                            state.setPlayer(player);
                            break;
                        }
                        case 'w': { // water
                            e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("water.gif"));
                            e.setMoveable(false);
                            state.listEntities().add(e);
                            break;
                        }
                        case '?': { // sky
                            e = new Entity(col * blocksize, row * blocksize, blocksize, blocksize, getSprite("sky.gif"));
                            e.setMoveable(false);
                            state.listEntities().add(e);
                            break;
                        }
                        case ' ': { // air
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

    public Sprite getSprite(String ref) {
        return new Sprite(textureLoader, ref);
    }
}