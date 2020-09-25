package model;

/* Represents where a shape is on the canvas.  Two points with (X,Y) coordinates. 
 * Represent upper left and lower right corners of the shape boundaries.
 */
public class ShapePosition {
	private PointInt left;
	private PointInt right;
	private PointInt pointStart;
	private PointInt pointEnd;

	public ShapePosition(PointInt pointStart, PointInt pointEnd) {
		this.pointStart = pointStart;
		this.pointEnd = pointEnd;
		this.left = calculateLeftPoint(pointStart, pointEnd);
		this.right = calculateRightPoint(pointStart, pointEnd);
	
	}
	
	private PointInt calculateLeftPoint(PointInt pointStart, PointInt pointEnd) {
		Integer leftx = pointStart.getX() < pointEnd.getX()
			? pointStart.getX()
			: pointEnd.getX();

		Integer lefty = pointStart.getY() < pointEnd.getY()
			? pointStart.getY()
			: pointEnd.getY();
			
		return new PointInt(leftx, lefty);
	}

	private PointInt calculateRightPoint(PointInt pointStart, PointInt pointEnd) {
		Integer rightx = pointEnd.getX() >= pointStart.getX()
			? pointEnd.getX()
			: pointStart.getX();
		
		Integer righty = pointEnd.getY() >= pointStart.getY()
			? pointEnd.getY()
			: pointStart.getY();

		return new PointInt(rightx, righty);
			
	}

	public PointInt getLeft() {
		return left;
	}

	public PointInt getRight() {
		return right;
	}

	public PointInt getStart() {
		return pointStart;
	}

	public PointInt getEnd() {
		return pointEnd;
	}
}
