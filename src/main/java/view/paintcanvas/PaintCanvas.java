package view.paintcanvas;

import java.awt.Graphics2D;

public class PaintCanvas extends AbstractPaintCanvas
{
    public Graphics2D getGraphics2D() {
        return (Graphics2D)getGraphics();
    }

}
