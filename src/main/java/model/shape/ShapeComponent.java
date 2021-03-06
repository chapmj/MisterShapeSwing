package model.shape;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IBoundary;
import model.interfaces.IShape;

import java.util.List;

/* Abstract ShapeComponent is a super type of Shape and ShapeGroup.  
 * COMPOSITE PATTERN
 */
public abstract class ShapeComponent implements IBoundary, IShape
{
	public abstract Integer getHeight();
	public abstract Integer getWidth();
	public abstract PointInt getAnchor();
	protected abstract IBoundary getBoundingPoints();
	public abstract Dimensions getDimensions();
	public abstract ShapePosition getPosition();
	public abstract List<IShape> getShapes();
	public abstract ShapeType getType();
	public abstract void setAnchor(PointInt anchor);
	public abstract void setHeight(Integer height);
	public abstract void setWidth(Integer width);

	//create null objects for these
	public ShapeCardinality getCardinality()
	{
		return null;
	}
	public ShapeStyle getStyle()
	{
		return null;
	}

	public ShapeColor getPrimaryColor()
	{
		return null;
	}
	public ShapeColor getSecondaryColor()
	{
		return null;
	}

}