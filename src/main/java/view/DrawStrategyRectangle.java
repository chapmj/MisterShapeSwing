package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

import controller.CanvasController;
import model.Selection;
import model.ShapeShadingType;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyRectangle extends DrawStrategy {

	private Stroke stroke;
	private CanvasController canvasController;
	private Graphics2D g2D;
	private IShape shape;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;
	private DrawStrategyCommon common;

	public DrawStrategyRectangle(CanvasController canvasController, ShapeComponent shape) {
		super();
		this.canvasController = canvasController;
		this.g2D = canvasController.getGraphics2D();
		this.shape = (IShape) shape;
		this.common = new DrawStrategyCommon((IShape) shape);
		setStyleParams();	
	}

	private void setStyleParams() {
		stroke = DrawStrategyCommon.makeStroke();
		primaryColor = common.getPrimaryColor(); 
		secondaryColor = common.getSecondaryColor(); 
		shadingType = common.getShadingType();
	}

	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void execute() {
		paintShapeWithShading();
		drawSelection();
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
		g2D.setColor(color);
		g2D.setStroke(stroke);
		g2D.drawRect(
			shape.getAnchor().getX(),
			shape.getAnchor().getY(),
			shape.getWidth(),
			shape.getHeight());
	}

	private void fillShape(Color color) {
		g2D.setColor(color);
		g2D.fillRect(
			shape.getAnchor().getX(),
			shape.getAnchor().getY(),
			shape.getWidth(),
			shape.getHeight());
	}

	private void drawSelection() {
		ArrayList<IShape> selectedShapes = canvasController.getShapeSelectionList();
		if (selectedShapes.contains(shape)) {
			IShape selection = (IShape) (new Selection((ShapeComponent) shape, 10).getSelectionShape());
			g2D.setColor(Color.BLACK);
			g2D.setStroke(stroke);
			g2D.drawRect(
				selection.getAnchor().getX(),
				selection.getAnchor().getY(),
				selection.getWidth(),
				selection.getHeight());	
		}
	}
}