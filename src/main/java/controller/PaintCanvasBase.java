package controller;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class PaintCanvasBase extends JComponent {
    public abstract Graphics2D getGraphics2D();
}
