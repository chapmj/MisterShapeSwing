package controller;

import controller.interfaces.IJPaintController;
import controller.interfaces.ISingleton;
import model.ShapeStyle;
import model.ShapeType;
import model.StartAndEndPointMode;
import model.interfaces.IApplicationState;
import view.EventName;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

/* JPaintController is responsible for application UI widgets and events 
 * outside the canvas.
 * SINGLETON PATTERN
 */

public class JPaintController implements IJPaintController, ISingleton {
    private final IUiModule uiModule;
	private final IApplicationState applicationState;
	private static JPaintController instance;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState, PaintCanvasBase paintCanvas) throws Exception {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
    }

	// Singleton creation code satisfies ISingleton needs.
	public static JPaintController getInstance() {
		if (instance == null) 
			return null;	
		else 
			return instance;
	}
	
	public static void createInstance(IUiModule uiModule, IApplicationState applicationState, PaintCanvasBase paintCanvas) throws Exception {
		if (instance == null)
			instance = new JPaintController(uiModule, applicationState, paintCanvas);
	}
	
	// Setup callbacks for UI buttons that change UI mode.
    @Override
    public void setup() {
        registerEvents();
    }

    private void registerEvents() {
        uiModule.addEvent(EventName.CHOOSE_SHAPE, () -> applicationState.setActiveShape());
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> applicationState.setActivePrimaryColor());
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> applicationState.setActiveSecondaryColor());
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> applicationState.setActiveShadingType());
        uiModule.addEvent(EventName.CHOOSE_START_POINT_ENDPOINT_MODE, () -> applicationState.setActiveStartAndEndPointMode());
    }

    public IUiModule getUiModule() {
		return uiModule;
	}

	public ShapeType getActiveShape() {
		return applicationState.getActiveShapeType();
	}
	
	public StartAndEndPointMode getStartAndEndPointMode() {
		return applicationState.getActiveStartAndEndPointMode();
	}
	
	public ShapeStyle getShapeStyleFromApp() {
		return applicationState.getShapeStyle();
	}
}