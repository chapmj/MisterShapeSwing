package model.shape;

import model.Dimensions;
import model.PointInt;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.IShape;

// A shape component that represents a drawable object for the canvas.
public class Shape extends ShapeComponent implements IShape, Cloneable {

	ShapeType type; 
	Dimensions dimensions;
	ShapeStyle style;
	ShapeCardinality cardinality;
	PointInt anchor;

	public Shape(ShapeType type, Dimensions dimensions, ShapeStyle style, ShapeCardinality cardinality, PointInt anchor) {
		this.dimensions = dimensions;
		this.style = style;
		this.cardinality = cardinality;
		this.type = type;
		this.anchor = anchor;
	}

	public Shape(ShapeType type, Dimensions dimensions, PointInt anchor) {
		this.type = type;
		this.dimensions = dimensions;
		this.anchor = anchor;

		this.style = null;
		this.cardinality = null;
	}

	public ShapeColor getPrimaryColor() {
		return style.primaryColor;
	}

	public ShapeColor getSecondaryColor() {
		return style.secondaryColor;
	}

	public ShapeType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Shape [type=" + type + ", dimensions=" + dimensions + ", style=" + style + ", cardinality="
				+ cardinality + ", anchor=" + anchor + "]";
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public ShapeStyle getStyle() {
		return style;
	}

	public ShapeCardinality getCardinality() {
		return cardinality;
	}

	public Integer getHeight() {
		return dimensions.getHeight();	
	}
	
	public Integer getWidth() {
		return dimensions.getWidth();
	}

	public void setHeight(Integer h) {
		dimensions.setHeight(h);	
	}

	public void setWidth(Integer w) {
		dimensions.setWidth(w);	
	}

	
	public PointInt getAnchor() {
		return anchor;
	}

	public void setAnchor(PointInt anchor) {
		this.anchor = anchor;
	}

	/*
	public void translate(PointInt translation) {
		CanvasUtils.moveShape(this, translation);
	}
	 */
	
	@Override
	public ShapeComponent clone() {
	    return (ShapeComponent) ShapeFactory.duplicateShape(this);
	}
	
	@Override
	public ShapeComponent getBoundingPoints() {
		return null;
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
		ArrayList<IShape> list = new ArrayList<>();
		list.add(this);
		return list;
	}
}

