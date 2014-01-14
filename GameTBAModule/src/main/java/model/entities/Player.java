package model.entities;

import view.Sprite;

/**
 * Created by Xabster on 11-01-14.
 */
public class Player extends Entity {
    public Player(Sprite sprite) {
        super(sprite);
    }

    public Player(float posX, float posY, float width, float height, Sprite sprite) {
        super(posX, posY, width, height, sprite);
    }

    public Player(float posX, float posY, float width, float height, Sprite sprite, float velocityX, float velocityY) {
        super(posX, posY, width, height, sprite, velocityX, velocityY);
    }

    public Player(float posX, float posY, float width, float height, Sprite sprite, float angle, float velocityX, float velocityY) {
        super(posX, posY, width, height, sprite, angle, velocityX, velocityY);
    }
}
