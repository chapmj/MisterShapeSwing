package controller.commands;

import controller.interfaces.IControllerTask;

public abstract class AbstractControllerTask implements IControllerTask
{
    public abstract void execute();

    public void undo()
    { }

    public void redo()
    { }

}
