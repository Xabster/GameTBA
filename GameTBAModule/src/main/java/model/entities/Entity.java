package model.entities;

import model.Game;
import view.Sprite;

import java.awt.*;

/**
 * Created by Xabster on 08-01-14.
 */
public class Entity {

    protected float posX, posY, width, height;
    protected float angle, dAngle;
    protected float velocityX, velocityY;

    protected Sprite sprite;

    protected Rectangle me = new Rectangle(), other = new Rectangle();

    protected boolean moveable;

    public Entity(Sprite sprite) {
        this(0, 0, 0, 0, sprite);
    }

    public Entity(float posX, float posY, float width, float height, Sprite sprite) {
        this(posX, posY, width, height, sprite, 0, 0);
    }

    public Entity(float posX, float posY, float width, float height, Sprite sprite, float velocityX, float velocityY) {
        this(posX, posY, width, height, sprite, velocityX, velocityY, 0);
    }

    public Entity(float posX, float posY, float width, float height, Sprite sprite, float angle, float velocityX, float velocityY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.angle = angle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Rectangle getOccupiedSpace() {
        return new Rectangle(Math.round(posX), Math.round(posY), Math.round(width), Math.round(height));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void moveRelative(float dx, float dy) {
        setPosX(getPosX() + dx);
        setPosY(getPosY() + dy);
    }

    public void setAbsoluteAngle(float angle) {
        this.angle = (float) (angle % (Math.PI * 2));
    }

    public float getCurrentAngle() {
        return angle;
    }

    public void setAngularVelocity(float dAngle) {
        this.dAngle = dAngle;
    }

    public boolean collidesWith(Entity otherEntity) {
        me.setBounds(Math.round(posX), Math.round(posY), Math.round(width), Math.round(height));
        other.setBounds(Math.round(otherEntity.getPosX()), Math.round(otherEntity.getPosY()), Math.round(otherEntity.getWidth()), Math.round(otherEntity.getHeight()));
        return me.intersects(other);
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public void draw() {
        sprite.draw((int) posX, (int) posY, (int) width, (int) height, angle);
    }

    public void update(long delta) {
        if (moveable) {
            velocityY += (delta * Game.GRAVITY) / 1000;
            posX += (delta * velocityX) / 1000;
            posY += (delta * velocityY) / 1000;
        }
        angle += (delta * dAngle) / 1000;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", width=" + width +
                ", height=" + height +
                ", angle=" + angle +
                ", dAngle=" + dAngle +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", sprite=" + sprite +
                ", me=" + me +
                ", other=" + other +
                ", moveable=" + moveable +
                '}';
    }
}