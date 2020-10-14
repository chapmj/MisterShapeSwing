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
public class DrawStrategyEllipse extends DrawStrategy {

	private final Graphics2D graphics;
	private ShapeShadingType shadingType;
	private DrawStrategyEllipseSelection selectionDrawStrat;

	public DrawStrategyEllipse(IShape shape, Graphics2D graphics) {
		super(shape);
		this.graphics = graphics;
		selectionDrawStrat = new DrawStrategyEllipseSelection(shape, graphics);
		setStyleParams();
	}

	@Override
	public void draw() {
		paintShapeWithShading();
		selectionDrawStrat.draw();
	}

	private void setStyleParams() {
		shadingType = this.getShadingType();
	}

	private void paintShapeWithShading() {
		switch(shadingType) {
			case FILLED_IN: 
				fillShape(this.getPrimaryColor());
				break;
			case OUTLINE:
				drawShape(this.getPrimaryColor());
				break;
			case OUTLINE_AND_FILLED_IN:
				fillShape(this.getPrimaryColor());
				drawShape(this.getSecondaryColor());
				break;
		}
	}

	/*
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
			graphics.drawOval(x, y, w, h);
		}
	}*/

	private void drawShape(Color color)
	{
		IBoundary shapeBoundary = shape;
		var x = shapeBoundary.getAnchor().getX();
		var y = shapeBoundary.getAnchor().getY();
		var w = shapeBoundary.getWidth();
		var h = shapeBoundary.getHeight();

		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawOval(x, y, w, h);
	}

	private void fillShape(Color color)
	{
		IBoundary shapeBoundary = shape;
		var x = shapeBoundary.getAnchor().getX();
		var y = shapeBoundary.getAnchor().getY();
		var w = shapeBoundary.getWidth();
		var h = shapeBoundary.getHeight();

		graphics.setColor(color);
		graphics.fillOval(x, y, w, h);
	}
}