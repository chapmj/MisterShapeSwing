package model.interfaces;

import model.shape.*;

public interface IApplicationState {
    //Mutators
    void setUiShape();

    void setUiPrimaryColor();

    void setUiSecondaryColor();

    void setUiShadingType();

    void setUiStartAndEndPointMode();

    ShapeType getShapeType();
    ShapeStyle getShapeStyle();
    ShapeColor getPrimaryColor();
    ShapeColor getSecondaryColor();
    ShapeShadingType getShadingType();

    StartAndEndPointMode getStartAndEndPointMode();
}
