package view.api;

import view.interfaces.IUiModule;
import view.viewstate.ViewState;

import java.util.function.Supplier;

public class UISvc
{
    private static final Supplier<IUiModule> uiSupplier = () -> ViewState.getUI();

    public static IUiModule getUI()
    {
        return uiSupplier.get();
    }

}
