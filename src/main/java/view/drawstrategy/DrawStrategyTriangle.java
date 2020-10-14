package view.drawstrategy;

import model.interfaces.IShape;
import model.shape.ShapeCardinality;
import model.shape.ShapeShadingType;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyTriangle extends DrawStrategy {
	private final DrawStrategy selectionDrawStrat;
	private Graphics2D graphics;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyTriangle(IShape shape, Graphics2D graphics)
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

		int x = shape.getAnchor().getX();
		int y = shape.getAnchor().getY();
		int w = shape.getWidth();
		int h = shape.getHeight();
		ShapeCardinality cardinality = shape.getCardinality();

		Polygon triangle = view.Polygon.createTrianglePolygon(x, y, w, h, cardinality);
		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawPolygon(triangle);
	}
	
	private void fillShape(Color color) {
		int x = shape.getAnchor().getX();
		int y = shape.getAnchor().getY();
		int w = shape.getWidth();
		int h = shape.getHeight();
		ShapeCardinality cardinality = shape.getCardinality();

		Polygon triangle = view.Polygon.createTrianglePolygon(x, y, w, h, cardinality);
		graphics.setColor(color);
		graphics.fillPolygon(triangle);
	}
}
