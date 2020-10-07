package model.interfaces;

import model.Dimensions;
import model.shape.*;

/* IShape is an interface to represent
 * shapes.  Each shape has a type and some generic dimensions.
 * Other characteristics of the concrete class are derived from these.
 * COMPOSITE PATTERN: Leaf
 */
public interface IShape extends IBoundary {
	ShapeCardinality getCardinality();
	ShapeStyle getStyle();
	//void translate(PointInt translation);
	ShapeColor getPrimaryColor();
	ShapeColor getSecondaryColor();
	Dimensions getDimensions();
	ShapeType getType();
	ShapeComponent clone();
//	void setAnchor(PointInt anchor);
//	void setHeight(Integer height);
//	void setWidth(Integer width);
}
