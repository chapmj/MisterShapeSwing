package controller;

import java.awt.Graphics2D;

import controller.PaintCanvasBase;

public class PaintCanvas extends PaintCanvasBase {

    public Graphics2D getGraphics2D() {
        return (Graphics2D)getGraphics();
    }
}
