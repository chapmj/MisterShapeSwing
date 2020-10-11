package view.drawstrategy;

import java.awt.*;

import model.Selection;
import model.shape.ShapeShadingType;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.persistence.ModelState;
import view.viewstate.ViewState;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyEllipse extends DrawStrategy {

	private Stroke stroke;
	private Graphics2D graphics;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;

	public DrawStrategyEllipse(IShape shape) {
		super(shape);
		this.graphics = ViewState.getGraphics();
		setStyleParams();
	}

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
		
	private void drawSelection() {
		//var selectedShapes = ModelState.getShapeSelectionList();
		var selectedShapes = ModelState.getShapeComponentSelectionList();
		if (selectedShapes.contains(shape)) {
			IShape selection = new Selection(shape, 10).getSelectionShape();
			
			graphics.setColor(Color.BLACK);
			graphics.setStroke(stroke);
			graphics.drawOval(
				selection.getAnchor().getX(),
				selection.getAnchor().getY(),
				selection.getWidth(),
				selection.getHeight());	
		}
	}

	private void drawShape(Color color) {
		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawOval(
			shape.getAnchor().getX(), 
			shape.getAnchor().getY(), 
			shape.getWidth(),
			shape.getHeight());
	}

	private void fillShape(Color color) {
		graphics.setColor(color);
		graphics.fillOval(
			shape.getAnchor().getX(), 
			shape.getAnchor().getY(), 
			shape.getWidth(),
			shape.getHeight());
	}
}