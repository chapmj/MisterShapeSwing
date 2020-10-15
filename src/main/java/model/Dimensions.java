package model;

import model.shape.ShapePosition;

// Size of a shape object
public class Dimensions
{
	Integer height;
	Integer width;

	public Dimensions(Integer width, Integer height)
	{
		this.width = width;
		this.height = height;
	}

	public static Dimensions newInstance(Dimensions dimensions)
	{
	    var width = dimensions.getWidth();
	    var height = dimensions.getHeight();
		return new Dimensions(width, height);
	}

	public Dimensions(ShapePosition position)
	{
		this.height = position.getRight().getY() - position.getLeft().getY();
		this.width = position.getRight().getX() - position.getLeft().getX();
	}


    public Integer getHeight()
	{
		return height;
	}

	public Integer getWidth()
	{
		return width;
	}
	
	public void setHeight(Integer h)
	{
		this.height = h;
	}
	
	public void setWidth(Integer w)
	{
		this.width = w;
	}
}
