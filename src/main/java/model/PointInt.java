package model;

//Java has a point class, but it works in doubles.  This version works in ints.
public class PointInt {
	private Integer x;
	private Integer y;

	public PointInt() {
		this.x = 0;
		this.y = 0;	
	}

	public PointInt(Integer x, Integer y) {
		this.x = x;
		this.y = y;	
	}
	
	public static PointInt createPoint(Integer x, Integer y) {
		return new PointInt(x,y);	
	}
	

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	// Make a deep copy of self.
	public PointInt clone() {
		return new PointInt(x,y);
	}
	
	public void match(PointInt point) {
		this.x = point.getX();
		this.y = point.getY();	
	}
	
	public void add(PointInt point) {
		this.x = this.x + point.getX();	
		this.y = this.y + point.getY();	
	}
	
	public void subtract(PointInt point) {
		this.x = this.x - point.getX();	
		this.y = this.y - point.getY();	
	}
}
