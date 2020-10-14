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
	private final DrawStrategy selectionDrawStrat;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyRectangle(IShape shape, Graphics2D graphics)
	{
		super(shape);
		this.graphics = graphics;
		this.shape = shape;
		setStyleParams();
		selectionDrawStrat = new DrawStrategyRectangleSelection(shape, graphics);
	}


	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		selectionDrawStrat.draw();
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

}