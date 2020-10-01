package model.interfaces;

import model.Dimensions;
import model.shape.Shape;
import model.shape.ShapeCardinality;
import model.shape.ShapeStyle;

// TODO: This was a failed pattern that I moved on from.
public interface IShapeAbstractFactory {
	//Rectangle createRectangle(Dimensions dimensions, ShapeStyle style);
	//Ellipse createEllipse(Dimensions dimensions, ShapeStyle style);
	//Triangle createTriangle(Dimensions dimensions, ShapeStyle style, Cardinality cardinality);
	Shape createShape(Dimensions dimensions, ShapeStyle style, ShapeCardinality cardinality);
}
