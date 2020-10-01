package controller;

import controller.commands.*;
import model.CommandHistory;
import model.PointInt;
import model.shape.ShapeComponent;
import model.persistence.CanvasStateSubject;
import model.persistence.ModelState;

import java.util.ArrayList;
import java.util.List;

/* Static class for pre-command, command, and post-command operations.
 * Command pattern is being used to wrap commands in objects so they 
 * may be passed around in the Command History undo and redo collections.
 */

public class CanvasControllerCommands {
	public static void initAfterCanvasStateSubject(){} //used to force java to load static members
	private static CanvasStateSubject canvasStateSubject = ModelState.getCanvasStateSubject();

	// Creates shape selections and update model state. 
	public static void saveSelection(PointInt startPoint, PointInt endPoint) {
		try {
			var task = new SaveSelectionTask(startPoint, endPoint);
			task.execute();
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	// Move selected shapes and update model state. 
	public static void moveSelection(PointInt startPoint, PointInt endPoint) {
		try {
			var task = new MoveSelectionTask(startPoint, endPoint);
			task.execute();
			CommandHistory.add(task);
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	// Draw shape to canvas and update model state.
	public static void draw(PointInt startPoint, PointInt endPoint) {
		try {
			var task = new DrawTask(startPoint, endPoint, ModelState.getApplicationState());
			task.execute();
			CommandHistory.add(task);
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	// Delete selected shapes and update model state.
	public static void deleteSelection() {
		try {
			List<ShapeComponent> selection = ModelState.getCanvasState().getComponentSelectionList();
			var task = new DeleteTask(selection);
			task.execute();
			CommandHistory.add(task);
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) { exception.printStackTrace(); }
	}

	// Copy selected shapes to model state.
	public static void copySelection() {
		try {
			var task = new CopyTask();
			task.execute();
		} catch (Exception exception) { exception.printStackTrace(); }
	}

	// Paste shapes from model stored in model's copy buffer.
	public static void pasteSelection() {
		try {
			var task = new PasteTask();
			task.execute();
			CommandHistory.add(task);
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) { exception.printStackTrace(); }
	}

	// Assign selected shapes to a group and update model state.
	@SuppressWarnings("unchecked")
	public static void groupSelection() {
		try {

			if(ModelState.getCanvasState() != null && ModelState.getCanvasState().getComponentSelectionList() != null)
			{
				var selection = (ArrayList<ShapeComponent>) ModelState.getCanvasState().getComponentSelectionList();
				var copiedSelection = selection.clone();

				var task = new GroupTask((List<ShapeComponent>) copiedSelection);
				task.execute();
				CommandHistory.add(task);
				canvasStateSubject.notifyObservers();
			}

		} catch (Exception exception) {exception.printStackTrace();}
		
	}

	// Split all groups in the current selection into shapes and update model state.
	@SuppressWarnings("unchecked")
	public static void ungroupSelection() {
		try {
			if(ModelState.getCanvasState() != null && ModelState.getCanvasState().getComponentSelectionList() != null)
			{
				var selection = (ArrayList<ShapeComponent>) ModelState.getCanvasState().getComponentSelectionList();
				var copiedSelection = selection.clone();
				var task = new UngroupTask((List<ShapeComponent>) copiedSelection);
				task.execute();
				CommandHistory.add(task);
				canvasStateSubject.notifyObservers();
			}
		} catch (Exception exception) {exception.printStackTrace();}
	}

	/* Commands are stored in Command history which manages undo/redo collections.
	 * Undo and Redo methods are defined in each command.
	 */
	public static void undo() {
		try {
			CommandHistory.undo();
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	public static void redo() {
		try {
			CommandHistory.redo();
			canvasStateSubject.notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
		
	}
}
