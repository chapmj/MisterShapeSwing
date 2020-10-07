package model.dialogs;

import model.shape.ShapeColor;
import model.shape.ShapeShadingType;
import model.shape.ShapeType;
import model.shape.StartAndEndPointMode;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.interfaces.IDialogChoice;

public class DialogProvider implements IDialogProvider {
    private final IDialogChoice<ShapeType> chooseShapeDialog;
    private final IDialogChoice<ShapeColor> choosePrimaryColorDialog;
    private final IDialogChoice<ShapeColor> chooseSecondaryColorDialog;
    private final IDialogChoice<ShapeShadingType> chooseShadingTypeDialog;
    private final IDialogChoice<StartAndEndPointMode> chooseStartAndEndPointModeDialog;

    public DialogProvider(IApplicationState applicationState) {
        chooseShapeDialog = new ChooseShapeDialog(applicationState);
        choosePrimaryColorDialog = new ChoosePrimaryColorDialog(applicationState);
        chooseSecondaryColorDialog = new ChooseSecondaryColorDialog(applicationState);
        chooseShadingTypeDialog = new ChooseShadingTypeDialog(applicationState);
        chooseStartAndEndPointModeDialog = new ChooseStartAndEndPointModeDialog(applicationState);
    }

    @Override
    public IDialogChoice<ShapeType> getChooseShapeDialog() {
        return chooseShapeDialog;
    }

    @Override
    public IDialogChoice<ShapeColor> getChoosePrimaryColorDialog() {
        return choosePrimaryColorDialog;
    }

    @Override
    public IDialogChoice<ShapeColor> getChooseSecondaryColorDialog() {
        return chooseSecondaryColorDialog;
    }

    @Override
    public IDialogChoice<ShapeShadingType> getChooseShadingTypeDialog() {
        return chooseShadingTypeDialog;
    }

    @Override
    public IDialogChoice<StartAndEndPointMode> getChooseStartAndEndPointModeDialog() {
        return chooseStartAndEndPointModeDialog;
    }
}
