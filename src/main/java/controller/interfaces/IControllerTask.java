package controller.interfaces;

public interface IControllerTask extends IUndoRedo, ICommand {
	void execute();
}
