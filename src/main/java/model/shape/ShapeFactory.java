package model.shape;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IShape;

public class ShapeFactory  {
	public static IShape createShape(ShapeType type, Dimensions dimensions, ShapeStyle style, ShapeCardinality cardinality, PointInt anchor) {
		return new Shape(type, dimensions, style, cardinality, anchor);
	}
	
	public static IShape duplicateShape(IShape shape) {
		return new Shape(
			shape.getType(), 
			shape.getDimensions().clone(), 
			shape.getStyle().clone(), 
			shape.getCardinality(), 
			shape.getAnchor().clone());
	}

	public static ShapeComponent duplicateShapeComponent(IShape shape) {

		if (shape instanceof Shape)
			return (ShapeComponent) duplicateShape(shape);

		return new Shape(
			shape.getType(), 
			shape.getDimensions().clone(), 
			shape.getAnchor().clone());
	}
}

