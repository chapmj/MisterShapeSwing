package view.paintcanvas;

import java.awt.*;

import javax.swing.JComponent;

public abstract class AbstractPaintCanvas extends JComponent {
    public abstract Graphics2D getGraphics2D();

}

