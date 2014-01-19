package platsnip.model;

import platsnip.model.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class State {

    private Entity player;

    private List<Entity> entities = new ArrayList<>(); // consider different collection - maybe HashSet?

    public static final int GRAVITY = 200;

    public List<Entity> listEntities() {
        return entities;
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }
}