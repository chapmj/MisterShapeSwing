package model;

import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

// TODO: Make this an actual factory.  Right now its just a static method to duplicate a shape component.
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

	public static ShapeComponent duplicateShapeComponent(ShapeComponent shape) {

		if (shape instanceof Shape)
			return (ShapeComponent) duplicateShape((IShape) shape);

		return (ShapeComponent) new Shape(
			shape.getType(), 
			shape.getDimensions().clone(), 
			shape.getAnchor().clone());
	}
}
