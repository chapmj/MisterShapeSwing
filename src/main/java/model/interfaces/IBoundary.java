package model.interfaces;

import model.PointInt;

public interface IBoundary {
	PointInt getAnchor();
	Integer getWidth();
	Integer getHeight();
}
