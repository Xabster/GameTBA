package model.entities;

import view.Sprite;

import java.awt.*;

/**
 * Created by Xabster on 08-01-14.
 */
public class Entity {

    protected float posX, posY, width, height;
    protected float angle;
    protected float velocityX, velocityY;

    protected Sprite sprite;

    protected Rectangle me = new Rectangle(), other = new Rectangle();

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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = (float)(angle % (Math.PI*2));
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

    public void update(long delta) {
        posX += (delta * velocityX) / 1000;
        posY += (delta * velocityY) / 1000;
    }

    public void draw() {
        sprite.draw((int) posX, (int) posY);
    }

    public void move(float dx, float dy) {
        setPosX(getPosX() + dx);
        setPosY(getPosY() + dy);
    }

    public void rotate(float dAngle) {
        setAngle(getAngle() + dAngle);
    }

    public boolean collidesWith(Entity otherEntity) {
        me.setBounds(Math.round(posX), Math.round(posY), Math.round(width), Math.round(height));
        other.setBounds(Math.round(otherEntity.getPosX()), Math.round(otherEntity.getPosY()), Math.round(otherEntity.getWidth()), Math.round(otherEntity.getHeight()));
        return me.intersects(other);
    }
}