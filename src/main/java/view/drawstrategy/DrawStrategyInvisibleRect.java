package view.drawstrategy;

import model.Selection;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.persistence.ModelState;
import view.viewstate.ViewState;

import java.awt.*;

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
	private Graphics2D graphics;
	private ShapeComponent group;
	private BasicStroke stroke;

	public DrawStrategyInvisibleRect(ShapeComponent group)
	{
		super();
		this.graphics = ViewState.getGraphics();
		this.group = group;
		setStyleParams();	
	}
	
	public void draw()
	{
		drawSelection();
	}

	private void setStyleParams()
	{
		stroke = DrawStrategyCommon.makeStroke();
	}
	
	private void drawSelection() {
		var selectedComponents= ModelState.getShapeComponentSelectionList();

		if (selectedComponents.contains(group)) {
			IShape selection = (IShape) (new Selection(group, 10).getSelectionShape());
			graphics.setColor(Color.RED);
			graphics.setStroke(stroke);
			graphics.drawRect(
				selection.getAnchor().getX(),
				selection.getAnchor().getY(),
				selection.getWidth(),
				selection.getHeight());	
		}
	}
}
