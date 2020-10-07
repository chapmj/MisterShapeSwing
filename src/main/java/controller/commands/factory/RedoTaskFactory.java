package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.RedoTask;

public class RedoTaskFactory extends AbstractTaskFactory
{
    @Override
    public AbstractControllerTask createTask()
    {
        return new RedoTask();
    }
}
