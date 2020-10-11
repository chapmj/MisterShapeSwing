package model.shape;

import java.util.ArrayList;
import java.util.List;

import model.Dimensions;
import model.PointInt;
import model.interfaces.IShape;

/* Part of a composite pattern / tree data structure. Represents a node.
 * ShapeGroups contain other shape groups or shapes.  Collectively called ShapeComponents.
 * COMPOSITE PATTERN: Node
 */
public class ShapeGroup extends ShapeComponent {
	private final List<ShapeComponent> children = new ArrayList<>();
	Integer cachedHeight = 0;
	Integer cachedWidth = 0;
	PointInt cachedAnchor = null;
	ShapeType shapeType = ShapeType.INVISIBLE_RECT;

	public ShapeGroup()
	{

	}

	public ShapeGroup(List<ShapeComponent> children)
	{
		//addChild(children);
		this.children.addAll(children);
		setGroupBounds();
	}

	public void addChild(List<ShapeComponent> selection)
	{
		children.addAll(selection);	
		setGroupBounds();
	}

	public void removeChild(List<ShapeComponent> selection)
	{
		children.removeAll(selection);
		setGroupBounds();	
	}

	private void setGroupBounds() {
		ShapeComponent bounds = getBoundingPoints();
		cachedAnchor = bounds.getAnchor();
		cachedHeight = bounds.getHeight();
		cachedWidth = bounds.getWidth();
	}	

	public ShapeComponent getBoundingPoints() {
	// Recursively get boundaries $$
		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		Integer xMin = Integer.MAX_VALUE;
		Integer yMin = Integer.MAX_VALUE;

		for (ShapeComponent child : children) {
			Integer width = child.getWidth();
			Integer height = child.getHeight();
			PointInt anchor = child.getAnchor();
			int xRight = width + anchor.getX();
			int yRight = height + anchor.getY();
			Integer xLeft = anchor.getX();
			Integer yLeft = anchor.getY();
			if (xRight > xMax) xMax = xRight;
			if (yRight > yMax) yMax = yRight;
			if (xLeft < xMin) xMin = xLeft;
			if (yLeft < yMin) yMin = yLeft;
		}

		return new Shape(ShapeType.RECTANGLE,new Dimensions(xMax - xMin, yMax - yMin), new PointInt(xMin,yMin));
	}
	
	public Integer getWidth() {
	// Recursively get width $$
		int xMax = Integer.MIN_VALUE;
		Integer xMin = Integer.MAX_VALUE;

		for (ShapeComponent child : children) {
			Integer width = child.getWidth();
			PointInt anchor = child.getAnchor();
			int xRight = width + anchor.getX();
			Integer xLeft = anchor.getX();
			if (xRight > xMax) xMax = xRight;
			if (xLeft < xMin) xMin = xLeft;
		}
		return xMax - xMin;
	}

	public Integer getHeight() {
	// Recursively get height $$
		int yMax = Integer.MIN_VALUE;
		Integer yMin = Integer.MAX_VALUE;

		for (ShapeComponent child : children) {
			Integer height = child.getHeight();
			PointInt anchor = child.getAnchor();
			int yRight = height + anchor.getY();
			Integer yLeft = anchor.getY();
			if (yRight > yMax) yMax = yRight;
			if (yLeft < yMin) yMin = yLeft;
		}
		return yMax - yMin;
	}

	public PointInt getAnchor() {
	// Recursively get anchor $$
		Integer xMin = Integer.MAX_VALUE;
		Integer yMin = Integer.MAX_VALUE;

		// Excuse me, I must please recurse myself 
		for (ShapeComponent child : children) {
			PointInt anchor = child.getAnchor();
			Integer xLeft = anchor.getX();
			Integer yLeft = anchor.getY();
			if (xLeft < xMin) xMin = xLeft;
			if (yLeft < yMin) yMin = yLeft;
		}
		return new PointInt(xMin, yMin);
	}

	@Override
	public ShapeCardinality getCardinality()
	{
		return null;
	}

	@Override
	public ShapeStyle getStyle()
	{
		return null;
	}

	@Override
	public ShapeColor getPrimaryColor()
	{
		return null;
	}

	@Override
	public ShapeColor getSecondaryColor()
	{
		return null;
	}

	public Dimensions getDimensions() {
		return new Dimensions(cachedHeight, cachedWidth);
	}

	@Override
	public ShapePosition getPosition() {
		return new ShapePosition(
			getAnchor(), 
			new PointInt(
				getAnchor().getX() + getWidth(), 
				getAnchor().getY() + getHeight()));	
	}

	@Override
	public List<IShape> getShapes() {
		List<IShape> list = new ArrayList<>();

		for (ShapeComponent child : children) {
			list.addAll(child.getShapes());
		}

		return list;
	}

	@Override
	public ShapeType getType() {
		return shapeType;
	}

	@Override
	public void setAnchor(PointInt anchor) {
		Integer dX = anchor.getX() - cachedAnchor.getX();
		Integer dY = anchor.getY() - cachedAnchor.getY();
		PointInt delta = new PointInt(dX,dY);
		children.forEach((shape) -> new PointInt(
				shape.getAnchor().getX() + delta.getX(),
				shape.getAnchor().getY() + delta.getY()));
		setGroupBounds();
		getAnchor();
	}

	@Override
	public void setHeight(Integer height) {
		
	}

	@Override
	public void setWidth(Integer width) {
		
	}

	// Deep copy the ShapeGroup and all of its children.
	@Override
	public ShapeComponent clone() {
		ShapeGroup dupeGroup;

		var shapes = this.getShapes();
		var dupeShapes = new ArrayList<ShapeComponent>();
			
		shapes.stream()
			.map((shape) -> ShapeFactory.duplicateShapeComponent((ShapeComponent) shape))
			.forEach(dupeShapes::add);
			
		dupeGroup = new ShapeGroup(dupeShapes);

		return dupeGroup;
	}

}
