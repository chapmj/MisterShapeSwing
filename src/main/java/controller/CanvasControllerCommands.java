package controller;

import java.util.ArrayList;

import controller.commands.CopyTask;
import controller.commands.DeleteTask;
import controller.commands.DrawTask;
import controller.commands.GroupTask;
import controller.commands.MoveSelectionTask;
import controller.commands.PasteTask;
import controller.commands.SaveSelectionTask;
import controller.commands.UngroupTask;
import controller.interfaces.ICanvasControllerCommand;
import model.CommandHistory;
import model.PointInt;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasStateSubject;

/* Static class for pre-command, command, and post-command operations.
 * Command pattern is being used to wrap commands in objects so they 
 * may be passed around in the Command History undo and redo collections.
 */

public class CanvasControllerCommands {
	public static void initAfterCanvasStateSubject(){}; //used to force java to load static members
	private static CanvasController canvasController = CanvasController.getInstance();
	private static CanvasStateSubject canvasStateSubject = canvasController.getCanvasStateSubject();

	// Creates shape selections and update model state. 
	public static void saveSelection(PointInt startPoint, PointInt endPoint) {
		try {
			SaveSelectionTask task = new SaveSelectionTask(startPoint, endPoint);
			task.execute();
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	// Move selected shapes and update model state. 
	public static void moveSelection(PointInt startPoint, PointInt endPoint) {
		try {
			ICanvasControllerCommand task = new MoveSelectionTask(startPoint, endPoint);
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	// Draw shape to canvas and update model state.
	public static void draw(PointInt startPoint, PointInt endPoint) {
		try {
			ICanvasControllerCommand task = new DrawTask(startPoint, endPoint);
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	// Delete selected shapes and update model state.
	public static void deleteSelection() {
		try {
			ArrayList<ShapeComponent> selection = canvasController.canvasState.getComponentSelectionList();
			ICanvasControllerCommand task = new DeleteTask(selection);
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	// Copy selected shapes to model state.
	public static void copySelection() {
		try {
			ICanvasControllerCommand task = new CopyTask();
			task.execute();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	// Paste shapes from model stored in model's copy buffer.
	public static void pasteSelection() {
		try {
			ICanvasControllerCommand task = new PasteTask();
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	// Assign selected shapes to a group and update model state.
	@SuppressWarnings("unchecked")
	public static void groupSelection() {
		try {
			ArrayList<ShapeComponent> selection = canvasController.canvasState.getComponentSelectionList();
			ArrayList<ShapeComponent> copiedSelection = (ArrayList<ShapeComponent>) selection.clone();

			ICanvasControllerCommand task = new GroupTask(copiedSelection);
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
		
	}

	// Split all groups in the current selection into shapes and update model state.
	@SuppressWarnings("unchecked")
	public static void ungroupSelection() {
		try {
			ArrayList<ShapeComponent> selection = canvasController.canvasState.getComponentSelectionList();
			ArrayList<ShapeComponent> copiedSelection = (ArrayList<ShapeComponent>) selection.clone();
			ICanvasControllerCommand task = new UngroupTask(copiedSelection);
			task.execute();
			CommandHistory.add(task);
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}

	/* Commands are stored in Command history which manages undo/redo collections.
	 * Undo and Redo methods are defined in each command.
	 */
	public static void undo() {
		try {
			CommandHistory.undo();
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
	}
	
	public static void redo() {
		try {
			CommandHistory.redo();
			notifyObservers();
		} catch (Exception exception) {exception.printStackTrace();}
		
	}

	/* Notify Observers that a command has finished updating model state.
	 * Post-execution step for most commands.
	 */
	private static void notifyObservers() {
		try {
			canvasStateSubject.notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
