package view.drawstrategy;

import model.interfaces.IShape;
import model.shape.ShapeColor;
import model.shape.ShapeShadingType;
import view.interfaces.IDrawStrategy;

import java.awt.BasicStroke;
import java.awt.Color;

public abstract class DrawStrategy implements IDrawStrategy
{
	public abstract void draw();
	protected IShape shape;
	protected BasicStroke stroke = makeStroke();

	public DrawStrategy()
	{ }

	public DrawStrategy(IShape shape)
	{
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

	public Color getPrimaryColor()
	{
		return toColor(shape.getPrimaryColor());
	}

	public Color getSecondaryColor()
	{
		return toColor(shape.getSecondaryColor());
	}

	public ShapeShadingType getShadingType()
	{
		return shape.getStyle().shadingType;
	}

	private Color toColor(ShapeColor color)
	{
		switch (color)
		{
			case BLACK: return Color.BLACK;
			case BLUE: return Color.BLUE;
			case CYAN: return Color.CYAN;
			case DARK_GRAY: return Color.DARK_GRAY;
			case GRAY: return Color.GRAY;
			case GREEN: return Color.GREEN;
			case LIGHT_GRAY: return Color.LIGHT_GRAY;
			case MAGENTA: return Color.MAGENTA;
			case ORANGE: return Color.ORANGE;
			case PINK: return Color.PINK;
			case RED: return Color.RED;
			case WHITE: return Color.WHITE;
			case YELLOW: return Color.YELLOW;
			default: return Color.BLACK;
		}
	}

}
