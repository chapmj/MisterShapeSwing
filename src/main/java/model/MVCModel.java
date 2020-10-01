package model;

import model.interfaces.IApplicationState;
import model.persistence.ApplicationState;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import view.EventName;
import view.interfaces.IUiModule;
import view.viewstate.ViewState;

public class MVCModel
{
    private static ModelState modelState;
    public MVCModel()
    { }

    public static void createModel() throws Exception
    {
        var applicationState = createApplicationState();
        var canvasState = new CanvasState();

        MVCModel.modelState = new ModelState(applicationState, canvasState);
    }

    private static IApplicationState createApplicationState() throws Exception
    {
        var ui = ViewState.getUI();
        var applicationState = new ApplicationState(ui);

        registerAppStateUIEvents(applicationState, ui);

        return applicationState;
    }

    private static void registerAppStateUIEvents(IApplicationState applicationState, IUiModule ui)
    {
        ui.addEvent(EventName.CHOOSE_SHAPE, applicationState::setUiShape);
        ui.addEvent(EventName.CHOOSE_PRIMARY_COLOR, applicationState::setUiPrimaryColor);
        ui.addEvent(EventName.CHOOSE_SECONDARY_COLOR, applicationState::setUiSecondaryColor);
        ui.addEvent(EventName.CHOOSE_SHADING_TYPE, applicationState::setUiShadingType);
        ui.addEvent(EventName.CHOOSE_START_POINT_ENDPOINT_MODE, applicationState::setUiStartAndEndPointMode);
    }


}
