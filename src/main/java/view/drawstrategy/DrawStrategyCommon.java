package view.drawstrategy;

import controller.CanvasUtils;
import model.interfaces.IShape;
import model.shape.ShapeShadingType;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyCommon {

	private IShape shape;

	public DrawStrategyCommon(IShape shape) {
		this.shape = shape;
	}

	public static BasicStroke makeStroke()
	{
		final float[] dash1 = {10.0f};
		final float width = 3.0f;
		final float miterlimit = 3.0f;
		final float dash_phase = 0.0f;
		return new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, miterlimit, dash1, dash_phase);
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
