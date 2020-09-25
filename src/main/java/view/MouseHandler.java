package view;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.MouseController;

// Catch mouse events
public class MouseHandler extends MouseAdapter {
	MouseController mouseController;
	
	public MouseHandler () {
		mouseController = MouseController.getInstance();	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseController.setPress(e.getX(),e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			mouseController.setRelease(e.getX(), e.getY());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}