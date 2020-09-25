package model.interfaces;

import model.ShapeStyle;
import model.ShapeType;
import model.StartAndEndPointMode;

public interface IApplicationState {
    void setActiveShape();
    void setActivePrimaryColor();
    void setActiveSecondaryColor();
    void setActiveShadingType();
    void setActiveStartAndEndPointMode();
    ShapeType getActiveShapeType();
    ShapeStyle getShapeStyle();
    StartAndEndPointMode getActiveStartAndEndPointMode();
}
