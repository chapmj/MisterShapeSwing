package model.persistence;

import java.io.Serializable;

import model.shape.ShapeColor;
import model.shape.ShapeShadingType;
import model.shape.ShapeStyle;
import model.shape.ShapeType;
import model.shape.StartAndEndPointMode;
import model.dialogs.DialogProvider;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.interfaces.IUiModule;

/* Stores settings for application UI.
 */
public class ApplicationState implements IApplicationState, Serializable {
    private static final long serialVersionUID = -5545483996576839008L;
    private final IUiModule uiModule;
    private final IDialogProvider dialogProvider;

    private ShapeType uiShapeType;
    private ShapeColor uiPrimaryColor;
    private ShapeColor uiSecondaryColor;
    private ShapeShadingType uiShapeShadingType;
    private StartAndEndPointMode uiStartAndEndPointMode;

    public ApplicationState(IUiModule uiModule)
    {
        this.uiModule = uiModule;
        this.dialogProvider = new DialogProvider(this);
        uiShapeType = ShapeType.ELLIPSE;
        uiPrimaryColor = ShapeColor.BLUE;
        uiSecondaryColor = ShapeColor.GREEN;
        uiShapeShadingType = ShapeShadingType.FILLED_IN;
        uiStartAndEndPointMode = StartAndEndPointMode.DRAW;
    }

    @Override
    public void setUiShape()
    {
        uiShapeType = uiModule.getDialogResponse(dialogProvider.getChooseShapeDialog());
    }

    @Override
    public void setUiPrimaryColor()
    {
        uiPrimaryColor = uiModule.getDialogResponse(dialogProvider.getChoosePrimaryColorDialog());
    }

    @Override
    public void setUiSecondaryColor()
    {
        uiSecondaryColor = uiModule.getDialogResponse(dialogProvider.getChooseSecondaryColorDialog());
    }

    @Override
    public void setUiShadingType()
    {
        uiShapeShadingType = uiModule.getDialogResponse(dialogProvider.getChooseShadingTypeDialog());
    }

    @Override
    public void setUiStartAndEndPointMode()
    {
        uiStartAndEndPointMode = uiModule.getDialogResponse(dialogProvider.getChooseStartAndEndPointModeDialog());
    }
    //end mutators

    @Override
    public ShapeType getShapeType()
    {
        return this.uiShapeType;
    }

    @Override
    public ShapeColor getPrimaryColor()
    {
        return this.uiPrimaryColor;
    }

    @Override
    public ShapeColor getSecondaryColor()
    {
        return this.uiSecondaryColor;
    }

    @Override
    public ShapeShadingType getShadingType()
    {
        return this.uiShapeShadingType;
    }

    @Override
    public StartAndEndPointMode getStartAndEndPointMode()
    {
        return this.uiStartAndEndPointMode;
    }
    
    public ShapeStyle getShapeStyle()
    {
        var x = new ShapeStyle(null,null,null);
        return new ShapeStyle(this.getPrimaryColor(), this.getSecondaryColor(), this.getShadingType());
    }
    
    private void setDefaults()
    {
        uiShapeType = ShapeType.ELLIPSE;
        uiPrimaryColor = ShapeColor.BLUE;
        uiSecondaryColor = ShapeColor.GREEN;
        uiShapeShadingType = ShapeShadingType.FILLED_IN;
        uiStartAndEndPointMode = StartAndEndPointMode.DRAW;
    }
}
