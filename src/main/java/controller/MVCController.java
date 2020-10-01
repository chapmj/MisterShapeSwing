package controller;

public class MVCController
{
    public static void createController() throws Exception
    {
        JPaintController.createInstance();
        MouseController.createInstance();
    }

}
