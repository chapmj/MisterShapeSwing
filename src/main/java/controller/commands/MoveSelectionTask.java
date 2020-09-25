package controller.commands;

import java.util.ArrayList;

import controller.CanvasController;
import controller.CanvasUtils;
import controller.interfaces.ICanvasControllerCommand;
import model.PointInt;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

// Responsible for moving all ShapeComponents in a selection to new coordinates.
public class MoveSelectionTask implements ICanvasControllerCommand {
	private CanvasState canvasState;
	private PointInt startPoint;
	private PointInt endPoint;
	private Integer deltaX;
	private Integer deltaY;
	private ArrayList<ShapeComponent> selection;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public MoveSelectionTask(PointInt startPoint, PointInt endPoint) {
		CanvasController canvasController = CanvasController.getInstance();
		this.canvasState = canvasController.getCanvasState();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		selection = new ArrayList<ShapeComponent> (canvasState.getComponentSelectionList() );
	}

	// Store relative change to position then move selected components.
	@Override
	public void execute() throws Exception {
		setDelta();
		move();
	}

	// The opposite of moving by a delta is moving by a minus delta.
	@Override
	public void undo() throws Exception {
		CanvasUtils.moveShapes(selection, new PointInt(-deltaX, -deltaY));
	}

	// Move stored selection.
	@Override
	public void redo() throws Exception {
		move();
	}

	// Move selection components by a delta relative point.
	private void move() throws Exception {
		CanvasUtils.moveShapes(selection, new PointInt(deltaX, deltaY));
	}
	
	private void setDelta() {
		deltaX = endPoint.getX() - startPoint.getX();
		deltaY = endPoint.getY() - startPoint.getY();
	}	
}
