package view.gui;

import java.awt.Graphics2D;

import view.interfaces.PaintCanvasBase;

public class PaintCanvas extends PaintCanvasBase {

    public Graphics2D getGraphics2D() {
        return (Graphics2D)getGraphics();
    }
}
