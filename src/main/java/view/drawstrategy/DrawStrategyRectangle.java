package view.drawstrategy;

import model.Selection;
import model.shape.ShapeShadingType;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.persistence.ModelState;
import view.viewstate.ViewState;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyRectangle extends DrawStrategy {

	private final Graphics2D graphics;
	private final IShape shape;
	private final DrawStrategyCommon common;
	private Stroke stroke;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyRectangle(ShapeComponent shape)
	{
		super();
		this.graphics = ViewState.getGraphics();
		this.shape = (IShape) shape;
		this.common = new DrawStrategyCommon((IShape) shape);
		setStyleParams();	
	}


	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		drawSelection();
	}

	private void setStyleParams() {
		stroke = DrawStrategyCommon.makeStroke();
		primaryColor = common.getPrimaryColor(); 
		secondaryColor = common.getSecondaryColor(); 
		shadingType = common.getShadingType();
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