package controller.interfaces;

// Allow a class to implement undo and redo actions
public interface IUndoRedo {
	void undo() throws Exception;
	void redo() throws Exception;
}
