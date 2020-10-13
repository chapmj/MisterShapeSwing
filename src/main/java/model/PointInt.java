package model;

//Java has a point class, but it works in doubles.  This version works in ints.
public class PointInt {
	private final Integer x;
	private final Integer y;

	public PointInt(Integer x, Integer y) {
		this.x = x;
		this.y = y;	
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	// Make a deep copy of self.
	public PointInt clone() {
		return new PointInt(x,y);
	}

	public PointInt add(int x, int y) {
		var newX = this.x + x;
		var newY = this.y + y;
		return new PointInt(newX, newY);
	}
	
	public PointInt subtract(int x, int y) {
		var newX = this.x - x;
		var newY = this.y - y;
		return new PointInt(newX, newY);
	}
}
