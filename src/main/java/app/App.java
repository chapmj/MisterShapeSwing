package app;

import controller.MVCController;
import model.MVCModel;
import view.viewstate.MVCView;

public class App
{
    public static void main (String[] args) throws Exception
    {
        // Create application UI.  GUI skeleton provided.
        // Starts event loop threads.
        MVCView.createView();
        MVCModel.createModel();
        MVCController.createController(); //create after model

        // Necessary to make the quirky Swing canvas stay up to date.
        Thread.sleep(500);

    }
}
