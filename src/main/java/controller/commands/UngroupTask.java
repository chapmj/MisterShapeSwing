package controller.commands;

import java.util.ArrayList;
import java.util.stream.Stream;

import controller.CanvasController;
import controller.interfaces.ICanvasControllerCommand;
import model.ShapeGroup;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;
import model.persistence.CanvasState;

/* Responsible for taking a selection containing shapes or groups
 * and combining them in a group. Add this data to model state.
 */
public class UngroupTask implements ICanvasControllerCommand {
	private ArrayList<ShapeComponent> selection;
	private ArrayList<ShapeComponent> groups;
	private CanvasController canvasController;
	private CanvasState canvasState;
	private ArrayList<ShapeComponent> groupedShapes;
	private ArrayList<ShapeComponent> ungroupedShapes;

	/* Initialize with data prior to execution. Data persists
	 * with object's lifetime to make undo/redo methods useful.
	 */
	public UngroupTask(ArrayList<ShapeComponent> selection) {
		this.canvasController = CanvasController.getInstance();
		this.selection = new ArrayList<ShapeComponent>(selection);
		this.canvasState = canvasController.getCanvasState();
		groups = new ArrayList<>();
		groupedShapes = new ArrayList<>();
		ungroupedShapes = new ArrayList<>();
	}

	//Split group, remove group from canvas, add child objects to canvas.
	@Override
	public void execute() throws Exception {
		initializeGroupedShapes();
		canvasController.removeComponents(groups);
		canvasController.addComponents(groupedShapes);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(ungroupedShapes);
		canvasState.addComponentSelection(groupedShapes);

	}

	// Set up instance fields.
	private void initializeGroupedShapes() {
		groups.clear();
		selection.stream()
			.filter ((component) -> component instanceof ShapeGroup)
			.forEach(groups::add);

		ungroupedShapes.clear();
		selection.stream()
			.filter ((component) -> component instanceof IShape)
			.forEach(ungroupedShapes::add);

		groupedShapes.clear();
		groups.stream()
			.flatMap(component -> Stream.of(splitGroup(component)))
			.forEach(groupedShapes::addAll);
	}
	
	// Flatten group to a list of shapes.
	private ArrayList<ShapeComponent> splitGroup(ShapeComponent component) {
		ArrayList<ShapeComponent> shapes = new ArrayList<>();
		if (component instanceof ShapeGroup) {
			component.getShapes().stream()
			.map (shape -> (ShapeComponent) shape)
			.forEach(shapes::add);
		}
		return shapes;
	}

	// Remove shapes from canvas and add back group.
	@Override
	public void undo() throws Exception {
		canvasController.removeComponents(groupedShapes);
		canvasController.addComponents(groups);
		canvasState.clearComponentSelectionList();
		canvasState.addComponentSelection(ungroupedShapes);
		canvasState.addComponentSelection(groups);
	}

	// Remove group and add back shapes.
	@Override
	public void redo() throws Exception {
		execute();
	}
}