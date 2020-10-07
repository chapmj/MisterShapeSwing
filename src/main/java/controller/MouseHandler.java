package controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.MouseController;

// Catch mouse events
public class MouseHandler extends MouseAdapter {

	public MouseHandler () { }

	@Override
	public void mousePressed(MouseEvent e) {
		MouseController.getInstance().setPress(e.getX(),e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			MouseController.getInstance().setRelease(e.getX(), e.getY());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}