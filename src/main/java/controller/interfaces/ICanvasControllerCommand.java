package controller.interfaces;

public interface ICanvasControllerCommand extends IUndoRedo {
	void execute() throws Exception;
}
