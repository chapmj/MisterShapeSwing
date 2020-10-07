package model;

import java.util.Stack;

import controller.interfaces.IUndoRedo;

// Template code provided.  Maintain undo and redo command stacks
public class CommandHistory {
	private static final Stack<IUndoRedo> undoStack = new Stack<>();
	private static final Stack<IUndoRedo> redoStack = new Stack<>();

	public static void add(IUndoRedo cmd) {
		undoStack.push(cmd);
		redoStack.clear();
	}
	
	public static void undo() throws Exception {
		boolean result = !undoStack.empty();
		if (result) {
			IUndoRedo c = undoStack.pop();
			redoStack.push(c);
			c.undo();
		}
	}

	public static void redo() throws Exception {
		boolean result = !redoStack.empty();
		if (result) {
			IUndoRedo c = redoStack.pop();
			undoStack.push(c);
			c.redo();
		}
	}
}