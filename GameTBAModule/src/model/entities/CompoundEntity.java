package model.entities;

import view.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xabster on 08-01-14.
 */
public class CompoundEntity extends Entity {

    protected List<Entity> children = new ArrayList<>();

    public CompoundEntity(Sprite sprite) {
        super(sprite);
    }

    public CompoundEntity(float posX, float posY, float width, float height, Sprite sprite) {
        super(posX, posY, width, height, sprite);
    }

    public CompoundEntity(float posX, float posY, float width, float height, Sprite sprite, float velocityX, float velocityY) {
        super(posX, posY, width, height, sprite, velocityX, velocityY);
    }

    public CompoundEntity(float posX, float posY, float width, float height, Sprite sprite, float angle, float velocityX, float velocityY) {
        super(posX, posY, width, height, sprite, angle, velocityX, velocityY);
    }

    public void addChild(Entity entity) {
        children.add(entity);
    }

    public void removeChild(Entity entity) {
        children.remove(entity);
    }

    public List<Entity> getChildren() {
        // unmodifiable because we want people to use add/remove methods so
        // we at least have a way of detection changes to the list
        // -- This doesn't ensure Entities stay the same, but no NPE's when looking them up
        return Collections.unmodifiableList(children);
    }
}