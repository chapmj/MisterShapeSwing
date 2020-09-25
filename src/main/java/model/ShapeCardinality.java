package model;

// Represent the direction which a shape is rotated.
public enum ShapeCardinality {
    NE,  // NorthEast
    SE,
    NW,
    SW;
	
	public static ShapeCardinality calculateCardinality(PointInt pointStart, PointInt pointEnd) {
		if (pointStart.getX() < pointEnd.getX()) {
		//East
			if (pointStart.getY() < pointEnd.getY()) {
			//South
				return SE;
			
			} else {
			//North
				return NE;
			}
		
		} else {
		//West
			if (pointStart.getY() < pointEnd.getY()) {
			//South
				return SW;
			
			} else {
			//North
				return NW;
			}
		}
	}

	public static ShapeCardinality calculateCardinality(ShapePosition position) {
		return calculateCardinality(position.getStart(), position.getEnd());
	}
}
