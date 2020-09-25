package view;

import java.awt.BasicStroke;
import java.awt.Color;

import controller.CanvasUtils;
import model.ShapeShadingType;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyCommon {

	private IShape shape;

	public DrawStrategyCommon(IShape shape) {
		this.shape = shape;
	}

	public DrawStrategyCommon(ShapeComponent group) {
	}
	
	
	public static BasicStroke makeStroke() {
		final float dash1[] = {10.0f};
		return new BasicStroke(3.0f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						3.0f, dash1, 0.0f);
	}
	
	public Color getPrimaryColor() {
		return CanvasUtils.toColor(shape.getPrimaryColor());
	}

	public Color getSecondaryColor() {
		return CanvasUtils.toColor(shape.getSecondaryColor()); 
	}

	public ShapeShadingType getShadingType() {
		return shape.getStyle().shadingType;
	}
	
}
