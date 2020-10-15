package view.drawstrategy;

import model.Selection;
import model.interfaces.IBoundary;
import model.interfaces.IShape;
import view.api.SelectionSvc;

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
	private IShape  group;

	public DrawStrategyInvisibleRect(IShape group, Graphics2D graphics)
	{
		super(group);
		this.graphics = graphics;
		this.group = group;
	}
	
	public void draw()
	{
		drawSelection();
	}

	private void drawSelection()
	{
		var selection = SelectionSvc.get();
		if (selection.contains(group))
		{
			IBoundary selectionBoundary = new Selection(group, 10).getSelectionShape();
			var x = selectionBoundary.getAnchor().getX();
			var y = selectionBoundary.getAnchor().getY();
			var w = selectionBoundary.getWidth();
			var h = selectionBoundary.getHeight();

			graphics.setColor(Color.RED);
			graphics.setStroke(stroke);
			graphics.drawRect(x, y, w, h);
		}
	}
}
