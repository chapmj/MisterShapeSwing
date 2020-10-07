package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.UndoTask;

public class UndoTaskFactory extends AbstractTaskFactory
{
    @Override
    public AbstractControllerTask createTask()
    {
        return new UndoTask();
    }
}
