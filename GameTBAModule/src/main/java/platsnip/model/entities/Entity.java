package platsnip.model.entities;

import platsnip.view.Sprite;

import java.awt.*;

public class Entity {
    protected float posX, posY, width, height;
    protected float angle, dAngle;
    protected float velocityX, velocityY;
    protected float accelerationX, accelerationY;

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
        this(posX, posY, width, height, sprite, 0, velocityX, velocityY);
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

    public float getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(float accelerationY) {
        this.accelerationY = accelerationY;
    }

    public float getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Rectangle getOccupiedSpace() {
        return new Rectangle(Math.round(posX), Math.round(posY), Math.round(width), Math.round(height));
    }

    public float getPositionX() {
        return posX;
    }

    public void setPositionX(float posX) {
        this.posX = posX;
    }

    public float getPositionY() {
        return posY;
    }

    public void setPositionY(float posY) {
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

    public void moveRelative(float dx, float dy) {
        setPositionX(getPositionX() + dx);
        setPositionY(getPositionY() + dy);
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
        other.setBounds(Math.round(otherEntity.getPositionX()), Math.round(otherEntity.getPositionY()), Math.round(otherEntity.getWidth()), Math.round(otherEntity.getHeight()));
        return me.intersects(other);
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public void draw() {
        if (sprite != null) {
            sprite.draw((int) posX, (int) posY, (int) width, (int) height, angle);
        }
    }

    public void update(long deltaTimeMs) {
        if (moveable) {
            setVelocityY(getVelocityY() + (deltaTimeMs * getAccelerationX()) / 1000);
            setVelocityY(getVelocityY() + (deltaTimeMs * getAccelerationY()) / 1000);

            moveRelative((deltaTimeMs * getVelocityX()) / 1000, (deltaTimeMs * getVelocityY()) / 1000);

            setAbsoluteAngle(getCurrentAngle() + (deltaTimeMs * dAngle) / 1000);
        }
    }

    public void hasCollidedWith(Entity otherEntity) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (Float.compare(entity.accelerationX, accelerationX) != 0) return false;
        if (Float.compare(entity.accelerationY, accelerationY) != 0) return false;
        if (Float.compare(entity.angle, angle) != 0) return false;
        if (Float.compare(entity.dAngle, dAngle) != 0) return false;
        if (Float.compare(entity.height, height) != 0) return false;
        if (moveable != entity.moveable) return false;
        if (Float.compare(entity.posX, posX) != 0) return false;
        if (Float.compare(entity.posY, posY) != 0) return false;
        if (Float.compare(entity.velocityX, velocityX) != 0) return false;
        if (Float.compare(entity.velocityY, velocityY) != 0) return false;
        if (Float.compare(entity.width, width) != 0) return false;
        if (me != null ? !me.equals(entity.me) : entity.me != null) return false;
        if (other != null ? !other.equals(entity.other) : entity.other != null) return false;
        if (sprite != null ? !sprite.equals(entity.sprite) : entity.sprite != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (posX != +0.0f ? Float.floatToIntBits(posX) : 0);
        result = 31 * result + (posY != +0.0f ? Float.floatToIntBits(posY) : 0);
        result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + (angle != +0.0f ? Float.floatToIntBits(angle) : 0);
        result = 31 * result + (dAngle != +0.0f ? Float.floatToIntBits(dAngle) : 0);
        result = 31 * result + (velocityX != +0.0f ? Float.floatToIntBits(velocityX) : 0);
        result = 31 * result + (velocityY != +0.0f ? Float.floatToIntBits(velocityY) : 0);
        result = 31 * result + (accelerationX != +0.0f ? Float.floatToIntBits(accelerationX) : 0);
        result = 31 * result + (accelerationY != +0.0f ? Float.floatToIntBits(accelerationY) : 0);
        result = 31 * result + (sprite != null ? sprite.hashCode() : 0);
        result = 31 * result + (me != null ? me.hashCode() : 0);
        result = 31 * result + (other != null ? other.hashCode() : 0);
        result = 31 * result + (moveable ? 1 : 0);
        return result;
    }
}