package controller.interfaces;

public interface ICanvasControllerCommand extends IUndoRedo, ICommand {
	void execute();
}
