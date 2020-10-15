package model;

import model.interfaces.IBoundary;
import model.interfaces.IShape;
import model.shape.ShapeFactory;

/* A selection is like a shape component, but it doesn't persist on the canvas.
 * Its purpose is to outline which shapes the user has selected.
 */
public class Selection implements IBoundary
{
	private Integer selectionFactor;
	private IShape shape;
	private Integer height;
	private Integer width;
	private PointInt anchor;

	public Selection(IShape shape, Integer selectionFactor)
	{
		this.shape = shape;
		this.selectionFactor = selectionFactor;
	}
	
	public IShape getSelectionShape()
	{
		int leftx = shape.getAnchor().getX() - selectionFactor;
		int lefty = shape.getAnchor().getY() - selectionFactor;
		this.anchor = new PointInt(leftx, lefty);
		this.height = shape.getHeight() + (2 * selectionFactor);
		this.width = shape.getWidth() + (2 * selectionFactor);
		IShape s = ShapeFactory.createShape(shape);
		s.setAnchor(this.anchor);
		s.setHeight(this.height);
		s.setWidth(this.width);
		return s;
	}

	@Override
	public PointInt getAnchor()
	{
		return anchor;
	}

	@Override
	public Integer getWidth()
	{
		return width;
	}

	@Override
	public Integer getHeight()
	{
		return height;
	}

	@Override
	public void setAnchor(PointInt anchor)
	{
		this.anchor = anchor;
	}

	@Override
	public void setHeight(Integer height)
	{
		this.height = height;
	}

	@Override
	public void setWidth(Integer width)
	{
		this.width = width;
	}
}
