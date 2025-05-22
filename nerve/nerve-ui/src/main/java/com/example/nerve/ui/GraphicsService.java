package com.example.nerve.ui;

import com.example.nerve.ui.model.Cycle;

import java.awt.*;

public class GraphicsService {
    private final Graphics graphics;
    private Color originalColor;

    public GraphicsService(Graphics g) {
        this.graphics = g;
        this.originalColor = g.getColor(); // Save the original color
    }

    // Change the color
    public void setColor(Color color) {
        originalColor = this.graphics.getColor();
        this.graphics.setColor(color);
    }

    // Restore the original color
    public void restoreColor() {
        this.graphics.setColor(originalColor);
    }

    // Draw a circle with optional text
    public void drawCircle(Cycle cycle, String text) {
        graphics.fillOval(cycle.getCenterX() - cycle.getRadius(), 
                          cycle.getCenterY() - cycle.getRadius(), 
                          cycle.getRadius() * 2, 
                          cycle.getRadius() * 2);
        if (text != null && !text.isEmpty()) {
            FontMetrics fm = graphics.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int x = cycle.getCenterX() - textWidth / 2;
            int y = cycle.getCenterY() + textHeight / 4; // Adjust vertically for centering
            drawString(text, x, y, Color.BLACK);
            restoreColor();
        }
    }

    // Draw a string at the specified coordinates with the given color
    public void drawString(String text, int x, int y, Color color) {
        originalColor = this.graphics.getColor(); // Save the original color
        setColor(color); // Set the specified color
        this.graphics.drawString(text, x, y); // Draw the string
        restoreColor(); // Restore the original color
    }

    public void connectCycles(Cycle from, Cycle to) {
        int[] edgeCoordinates = CircleEdgeDrawer.calculateEdgeCoordinates(from, to);
        drawDirectedLine(
                edgeCoordinates[0], edgeCoordinates[1],
                edgeCoordinates[2], edgeCoordinates[3]);
    }

    // Draw an arrow line
    public void drawDirectedLine(int x1, int y1, int x2, int y2) {
        // Draw the main line
        graphics.drawLine(x1, y1, x2, y2);

        // Calculate the direction of the arrow
        double dx = x2 - x1;
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);

        // Draw two sides of the arrow
        for (int i = 0; i < 2; i++) {
            double arrowAngle = angle + (i == 0 ? Math.toRadians(30) : -Math.toRadians(30));
            int endX = (int) (x2 - 8 * Math.cos(arrowAngle));
            int endY = (int) (y2 - 8 * Math.sin(arrowAngle));
            graphics.drawLine(x2, y2, endX, endY);
        }
    }
}
