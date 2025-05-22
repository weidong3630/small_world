package com.example.nerve.ui;

import com.example.nerve.ui.model.Cycle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SmallWorldUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Color nodeColor = Color.RED; // 初始颜色为红色
    private boolean isRed = true; // 新增：记录当前颜色状态

    public SmallWorldUI() {
        setTitle("Small World Network Visualization");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a panel for drawing the Small World network
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSmallWorldNetwork(g);
            }
        };

        // Create a button to change node color
        JButton changeColorButton = new JButton("Toggle Node Color");

        changeColorButton.addActionListener(e -> {
            // 修改：点击时切换颜色
            if (isRed) {
                nodeColor = Color.BLUE;
                isRed = false;
            } else {
                nodeColor = Color.RED;
                isRed = true;
            }
            repaint(); // 刷新界面
        });

        // Add the panel and button to the window
        add(panel, BorderLayout.CENTER);
        add(changeColorButton, BorderLayout.SOUTH);
    }

    private void drawSmallWorldNetwork(Graphics g) {
        GraphicsService graphicsService = new GraphicsService(g); // Use GraphicsService

        // Draw the title
        graphicsService.drawString(
                "Small World Network Visualization",
                300, 100, Color.BLUE);

        // Create a list of cycles
        java.util.List<Cycle> cycles = new ArrayList<>();
        final int RADIUS = 50; // Define node radius constant
        for (int i = 0; i < 2; i++) {
            int x = 100 + i * 200;
            int y = 300;
            cycles.add(new Cycle(x, y, RADIUS));
        }

        // Add cycles on another line.
        for (int i = 0; i < 2; i++) {
            int x = 100 + i * 200;
            int y = 450;
            cycles.add(new Cycle(x, y, RADIUS));
        }

        // Draw nodes using the list of cycles
        graphicsService.setColor(nodeColor);  // Use variable color
        for (int i = 0; i < cycles.size(); i++) {
            Cycle cycle = cycles.get(i);
            graphicsService.drawCircle(cycle, "Node " + i); // Add node number
        }

        // Connect cycles with directed lines
        graphicsService.setColor(Color.MAGENTA);
        for (int i = 0; i < cycles.size() - 1; i++) {
            Cycle currentCycle = cycles.get(i);
            Cycle nextCycle = cycles.get(i + 1);
            graphicsService.connectCycles(currentCycle, nextCycle);
        }

        graphicsService.connectCycles(cycles.get(0), cycles.get(2));

        // Restore the original color
        graphicsService.restoreColor();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmallWorldUI ui = new SmallWorldUI();
            ui.setVisible(true);
        });
    }
}
