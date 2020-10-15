package model.shape;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IBoundary;
import model.interfaces.IShape;

import java.util.ArrayList;
import java.util.List;

/* Part of a composite pattern / tree data structure. Represents a node.
 * ShapeGroups contain other shape groups or shapes.  Collectively called ShapeComponents.
 * COMPOSITE PATTERN: Node
 */
public class ShapeGroup extends ShapeComponent
{
	private final List<IShape> children = new ArrayList<>();
	private final ShapeType shapeType = ShapeType.INVISIBLE_RECT;
	private Integer cachedHeight = 0;
	private Integer cachedWidth = 0;
	private PointInt cachedAnchor = new PointInt(0, 0);

	private ShapeGroup()
	{ }

	public ShapeGroup(List<IShape> children)
	{
		this.children.addAll(children);
		setGroupBounds();
	}

	private void setGroupBounds()
	{
		IBoundary bounds = getBoundingPoints();
		cachedAnchor = bounds.getAnchor();
		cachedHeight = bounds.getHeight();
		cachedWidth = bounds.getWidth();
	}	

	public IBoundary getBoundingPoints()
	{
	// Recursively get boundaries $$
		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;

		for (IShape child : children)
		{

			int xLeft = child.getAnchor().getX();
			int yLeft = child.getAnchor().getY();

			int xRight = child.getWidth() + xLeft;
			int yRight = child.getHeight() + xRight;

			if (xRight > xMax) xMax = xRight;
			if (yRight > yMax) yMax = yRight;
			if (xLeft < xMin) xMin = xLeft;
			if (yLeft < yMin) yMin = yLeft;
		}

		var type = ShapeType.RECTANGLE;
		var dim = new Dimensions(xMax - xMin, yMax - yMin);
		var anchor = new PointInt(xMin, yMin);

		return ShapeFactory.createShape(type, dim, null, null, anchor);
		//return new Shape(ShapeType.RECTANGLE,new Dimensions(xMax - xMin, yMax - yMin), new PointInt(xMin,yMin));
	}
	
	public Integer getWidth()
	{
	// Recursively get width $$
		int xMax = Integer.MIN_VALUE;
		Integer xMin = Integer.MAX_VALUE;

		for (IShape child : children)
		{
			Integer width = child.getWidth();
			PointInt anchor = child.getAnchor();
			int xRight = width + anchor.getX();
			Integer xLeft = anchor.getX();
			if (xRight > xMax) xMax = xRight;
			if (xLeft < xMin) xMin = xLeft;
		}

		return xMax - xMin;
	}

	public Integer getHeight()
	{
	// Recursively get height $$
		int yMax = Integer.MIN_VALUE;
		Integer yMin = Integer.MAX_VALUE;

		for (IShape child : children)
		{
			Integer height = child.getHeight();
			PointInt anchor = child.getAnchor();
			int yRight = height + anchor.getY();
			Integer yLeft = anchor.getY();
			if (yRight > yMax) yMax = yRight;
			if (yLeft < yMin) yMin = yLeft;
		}

		return yMax - yMin;
	}

	public PointInt getAnchor()
	{
	// Recursively get anchor $$
		Integer xMin = Integer.MAX_VALUE;
		Integer yMin = Integer.MAX_VALUE;

		// Excuse me, I must please recurse myself 
		for (IShape child : children)
		{
			PointInt anchor = child.getAnchor();
			Integer xLeft = anchor.getX();
			Integer yLeft = anchor.getY();
			if (xLeft < xMin) xMin = xLeft;
			if (yLeft < yMin) yMin = yLeft;
		}

		return new PointInt(xMin, yMin);
	}

	public Dimensions getDimensions()
	{
		return new Dimensions(cachedWidth, cachedHeight);
	}

	@Override
	public ShapePosition getPosition()
	{
	    var x = getAnchor().getX();
	    var y = getAnchor().getY();
	    var w = getWidth();
	    var h = getHeight();
	    var anchorLeft = getAnchor();
	    var anchorRight = new PointInt( x + w, y + h);

		return new ShapePosition(anchorLeft, anchorRight);
	}

	@Override
	public List<IShape> getShapes()
	{
		return new ArrayList<>(children);
	}

	@Override
	public ShapeType getType()
	{
		return shapeType;
	}

	@Override
	public void setAnchor(PointInt anchor)
	{
		int dX = anchor.getX() - cachedAnchor.getX();
		int dY = anchor.getY() - cachedAnchor.getY();

		children.forEach((shape) ->
		{
			var x = shape.getAnchor().getX();
			var y = shape.getAnchor().getY();

		    shape.setAnchor(new PointInt(x + dX, y + dY));
		});

		setGroupBounds();
	}

	@Override
	public void setHeight(Integer height)
	{ }

	@Override
	public void setWidth(Integer width)
	{ }

	public void add(IShape shape)
	{
		children.add(shape);
	}
}
