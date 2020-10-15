package model.shape;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IShape;
import model.persistence.ModelState;

import java.util.stream.Collectors;

public class ShapeFactory
{
	//Leaf copy constructor
	public static IShape createShape(Shape shape)
	{
		var type = shape.getType();
		var dimensions = shape.getDimensions();
		var style = shape.getStyle();
		var cardinality = shape.getCardinality();
		var anchor = shape.getAnchor();

		return new Shape(type,Dimensions.newInstance(dimensions), ShapeStyle.newInstance(style), cardinality,
				PointInt.newInstance(anchor));
	}

	//Composite copy constructor
	public static IShape createShape(ShapeGroup shapeGroup)
	{
	    var children = shapeGroup.getShapes();

		var dupChildren= children.stream()
				.map((ishape) -> (Shape) ishape)
				.map(ShapeFactory::createShape)
				.collect(Collectors.toList());

	    return new ShapeGroup(dupChildren);
	}

	public static IShape createShape(IShape ishape)
	{
		if (ishape instanceof Shape)
		{
			return ShapeFactory.createShape((Shape)ishape);
		}

		if (ishape instanceof ShapeGroup)
		{
			return ShapeFactory.createShape((ShapeGroup)ishape);
		}

		try
		{
			throw new Exception("ShapeFactory: unknown shape class");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	//position-based custom constructor
	public static IShape createShape(PointInt startPoint, PointInt endPoint)
	{
		var shapeType = ModelState.getApplicationState().getShapeType();
		var shapeStyle = ModelState.getApplicationState().getShapeStyle();

		var position = new ShapePosition(startPoint, endPoint);
		var dimensions = new Dimensions(position);
		var shapeCardinality = ShapeCardinality.calculateCardinality(position);

		return new Shape(shapeType, dimensions, shapeStyle, shapeCardinality, position.getLeft());

	}

	//detail-based custom constructor
	public static IShape createShape(ShapeType type, Dimensions dimensions, ShapeStyle style, ShapeCardinality cardinality, PointInt anchor)
	{
		return new Shape(type, dimensions, style, cardinality, anchor);
	}

}

