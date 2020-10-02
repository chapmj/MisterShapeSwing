package controller.commands;

import controller.interfaces.ICanvasControllerCommand;

public abstract class AbstractControllerCommand implements ICanvasControllerCommand
{
    public abstract void execute();

    public void undo()
    { }

    public void redo()
    { }

}
