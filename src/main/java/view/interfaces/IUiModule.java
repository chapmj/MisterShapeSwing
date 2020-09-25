package view.interfaces;

import view.EventName;

@SuppressWarnings("rawtypes")
public interface IUiModule {
    void addEvent(EventName eventName, IEventCallback command);
	<T> T getDialogResponse(IDialogChoice dialogChoice);
}
