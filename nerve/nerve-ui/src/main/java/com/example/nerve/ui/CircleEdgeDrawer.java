package com.example.nerve.ui;

import com.example.nerve.ui.model.Cycle;

public class CircleEdgeDrawer {
    // Calculate the coordinates for the edge between two circles
    public static int[] calculateEdgeCoordinates(Cycle cycle1, Cycle cycle2) {
        // Calculate the vector between the centers of the two circles
        double dx = cycle2.getCenterX() - cycle1.getCenterX();
        double dy = cycle2.getCenterY() - cycle1.getCenterY();
        // Calculate the length of the vector
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Calculate the unit vector
        double ux = dx / distance;
        double uy = dy / distance;
        
        // Calculate the start point (on the boundary of the first circle)
        int startX = (int) (cycle1.getCenterX() + ux * cycle1.getRadius());
        int startY = (int) (cycle1.getCenterY() + uy * cycle1.getRadius());
        
        // Calculate the end point (on the boundary of the second circle)
        int endX = (int) (cycle2.getCenterX() - ux * cycle2.getRadius());
        int endY = (int) (cycle2.getCenterY() - uy * cycle2.getRadius());
        
        return new int[]{startX, startY, endX, endY};
    }
}