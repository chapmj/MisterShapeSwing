package view.ui;

import view.EventName;
import view.interfaces.IEventCallback;
import view.interfaces.IUiModule;

public class UISvc
{
    static IUiModule ui;

    public static void init(IUiModule ui)
    {
        UISvc.ui = ui;
    }

    public static IUiModule getUI()
    {
        return ui;
    }

    public static void addGuiEvent(EventName eventName, IEventCallback callback)
    {
        ui.addEvent(eventName, callback);
    }

}
