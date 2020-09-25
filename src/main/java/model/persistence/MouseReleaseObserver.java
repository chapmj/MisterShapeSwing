package model.persistence;

import controller.MouseController;
import model.interfaces.IObservableObserver;

// Watch for when mouse release state data is updated.
public class MouseReleaseObserver implements IObservableObserver {

	private MouseController mouseController;

	public MouseReleaseObserver() {
		mouseController = MouseController.getInstance();
	}

	@Override
	public void update() throws Exception {
		mouseController.mouseReleaseAction();
	}

}
