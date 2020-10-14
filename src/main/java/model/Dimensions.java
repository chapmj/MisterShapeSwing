package model;

import model.shape.ShapePosition;

// Size of a shape object
public class Dimensions {
	Integer height;
	Integer width;

	public Dimensions(Integer XwX , Integer XhX)
	{
		this.width = XwX;
		this.height = XhX;
	}

	public Dimensions(Dimensions dimensions)
	{
		this.width = dimensions.getWidth();
		this.height = dimensions.getHeight();
	}

	public Dimensions(ShapePosition position) {
		this.height = position.getRight().getY() - position.getLeft().getY();
		this.width = position.getRight().getX() - position.getLeft().getX();
	}

	public Integer getHeight() {
		return height;
	}
	public Integer getWidth() {
		return width;
	}
	
	public void setHeight(Integer h) {
		this.height = h;

	}
	
	public void setWidth(Integer w) {
		this.width = w;
	}
	
	// Create a deep copy of self.
    /*
	public Dimensions clone() {
		
		return new Dimensions(width,height);
	}*/
}
