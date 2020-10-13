package model.interfaces;

import model.PointInt;

public interface IBoundary {
	PointInt getAnchor();
	Integer getWidth();
	Integer getHeight();
	void setAnchor(PointInt anchor);
	void setHeight(Integer height);
	void setWidth(Integer width);
}
