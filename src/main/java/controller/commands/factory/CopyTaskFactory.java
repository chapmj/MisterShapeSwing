package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.CopyTask;

public class CopyTaskFactory extends AbstractTaskFactory
{
    @Override
    public AbstractControllerTask createTask()
    {
        return new CopyTask();
    }
}
