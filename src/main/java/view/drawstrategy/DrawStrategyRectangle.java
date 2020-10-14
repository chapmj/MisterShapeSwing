package view.drawstrategy;

import model.Selection;
import model.interfaces.IBoundary;
import model.interfaces.IShape;
import model.shape.ShapeShadingType;
import view.api.SelectionSvc;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyRectangle extends DrawStrategy {

	private final Graphics2D graphics;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyRectangle(IShape shape, Graphics2D graphics)
	{
		super(shape);
		this.graphics = graphics;
		this.shape = shape;
		setStyleParams();
	}


	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		drawSelection();
	}

	private void setStyleParams() {
		primaryColor = this.getPrimaryColor();
		secondaryColor = this.getSecondaryColor();
		shadingType = this.getShadingType();
	}

	private void paintShapeWithShading() {
		switch(shadingType) {
			case FILLED_IN: 
				fillShape(primaryColor);
				break;
			case OUTLINE:
				drawShape(primaryColor);
				break;
			case OUTLINE_AND_FILLED_IN:
				fillShape(primaryColor);
				drawShape(secondaryColor);
				break;
		}
	}

	private void drawShape(Color color) {

		IBoundary shapeBoundary = shape;
		var x = shapeBoundary.getAnchor().getX();
		var y = shapeBoundary.getAnchor().getY();
		var w = shapeBoundary.getWidth();
		var h = shapeBoundary.getHeight();

		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawRect(x, y, w, h);
	}

	private void fillShape(Color color) {

		IBoundary shapeBoundary = shape;
	    var x = shapeBoundary.getAnchor().getX();
		var y = shapeBoundary.getAnchor().getY();
		var w = shapeBoundary.getWidth();
		var h = shapeBoundary.getHeight();

		graphics.setColor(color);
		graphics.fillRect(x, y, w, h);
	}

	private void drawSelection() {
		var selection = SelectionSvc.get();
		if(selection.contains(this.shape))
		{
			IBoundary selectionBoundary = new Selection(shape, 10).getSelectionShape();
			var x = selectionBoundary.getAnchor().getX();
			var y = selectionBoundary.getAnchor().getY();
			var w = selectionBoundary.getWidth();
			var h = selectionBoundary.getHeight();

			graphics.setColor(Color.BLACK);
			graphics.setStroke(stroke);
			graphics.drawRect(x, y, w, h);
		}
	}
}