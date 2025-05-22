package com.example.nerve.ui.model;

/**
 * Represents a cycle (circle) with a center point and radius.
 */
public class Cycle {
    private int centerX;
    private int centerY;
    private int radius;

    /**
     * Constructs a new Cycle with the specified center coordinates and radius.
     *
     * @param centerX the x-coordinate of the center
     * @param centerY the y-coordinate of the center
     * @param radius the radius of the cycle
     */
    public Cycle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * Returns the x-coordinate of the center.
     *
     * @return the x-coordinate of the center
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * Sets the x-coordinate of the center.
     *
     * @param centerX the new x-coordinate of the center
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * Returns the y-coordinate of the center.
     *
     * @return the y-coordinate of the center
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * Sets the y-coordinate of the center.
     *
     * @param centerY the new y-coordinate of the center
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * Returns the radius of the cycle.
     *
     * @return the radius of the cycle
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the cycle.
     *
     * @param radius the new radius of the cycle
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
}