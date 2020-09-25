package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import controller.CanvasController;
import model.Selection;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 * 
 * Invisible rectangle is a border for grouped shapes.
 * Invisible rectangles have coordinates but no shape object.
 * This is to allow a group boundary to not be filled, but 
 * it can be selected.
 */

public class DrawStrategyInvisibleRect extends DrawStrategy {
	private CanvasController canvasController;
	private Graphics2D g2D;
	private ShapeComponent group;
	private BasicStroke stroke;

	public DrawStrategyInvisibleRect(ShapeComponent group) {
		super();
		this.canvasController = CanvasController.getInstance();
		this.g2D = canvasController.getGraphics2D();
		this.group = group;
		setStyleParams();	
	}
	
	public void execute() {
		drawSelection();
	}

	private void setStyleParams() {
		stroke = DrawStrategyCommon.makeStroke();
	}
	
	private void drawSelection() {
		ArrayList<ShapeComponent> selectedComponents = canvasController.getShapeComponentSelectionList();
		if (selectedComponents.contains(group)) {
			IShape selection = (IShape) (new Selection(group, 10).getSelectionShape());
			g2D.setColor(Color.RED);
			g2D.setStroke(stroke);
			g2D.drawRect(
				selection.getAnchor().getX(),
				selection.getAnchor().getY(),
				selection.getWidth(),
				selection.getHeight());	
		}
	}
}
