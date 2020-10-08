package view.drawstrategy;

import model.Selection;
import model.interfaces.IShape;
import model.persistence.ModelState;
import model.shape.ShapeComponent;
import model.shape.ShapeShadingType;
import view.viewstate.ViewState;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyRectangle extends DrawStrategy {

	private final Graphics2D graphics;
	private Stroke stroke;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyRectangle(ShapeComponent shape)
	{
		super((IShape) shape);
		this.graphics = ViewState.getGraphics();
		this.shape = (IShape) shape;
		setStyleParams();
	}


	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		drawSelection();
	}

	private void setStyleParams() {
		stroke = DrawStrategy.makeStroke();
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
		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawRect(
			shape.getAnchor().getX(),
			shape.getAnchor().getY(),
			shape.getWidth(),
			shape.getHeight());
	}

	private void fillShape(Color color) {
		graphics.setColor(color);
		graphics.fillRect(
			shape.getAnchor().getX(),
			shape.getAnchor().getY(),
			shape.getWidth(),
			shape.getHeight());
	}

	private void drawSelection() {
		var selectedShapes = ModelState.getShapeSelectionList();
		if (selectedShapes.contains(shape)) {
			IShape selection = (IShape) (new Selection((ShapeComponent) shape, 10).getSelectionShape());
			graphics.setColor(Color.BLACK);
			graphics.setStroke(stroke);
			graphics.drawRect(
				selection.getAnchor().getX(),
				selection.getAnchor().getY(),
				selection.getWidth(),
				selection.getHeight());	
		}
	}
}