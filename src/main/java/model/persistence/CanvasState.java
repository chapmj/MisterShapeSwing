package model.persistence;

import java.util.ArrayList;
import java.util.stream.Collectors;

import controller.JPaintController;
import model.PointInt;
import model.ShapeGroup;
import model.interfaces.ICanvasState;
import model.interfaces.IShape;
import model.interfaces.ShapeComponent;

/* Stores data for interactions with the canvas.
 */
public class CanvasState implements ICanvasState {

	private PointInt lastPasteLocation;
	private PointInt mousePressed;
	private PointInt mouseReleased;
	private ArrayList<ShapeGroup> shapeGroups;
	private ArrayList<ShapeComponent> componentSelectionList;
	private ArrayList<ShapeComponent> componentList; 
	private ArrayList<ShapeComponent> componentCopyBuffer;

	public CanvasState(JPaintController controller) {
		this.mousePressed = new PointInt();
		this.mouseReleased = new PointInt();
		this.shapeGroups = new ArrayList<>();
		this.componentSelectionList = new ArrayList<>();
		this.componentList = new ArrayList<>();
		this.componentCopyBuffer  = new ArrayList<>();
		this.lastPasteLocation = new PointInt(0,0);
	}

	// DRAWABLES

	public void addComponent(ArrayList<ShapeComponent> components) {
		componentList.addAll(components);
	}

	public void removeComponent(ArrayList<ShapeComponent> components) {
		componentList.removeAll(components);
	}

	public void removeComponent(ShapeComponent shapeComponent) {
		componentList.remove(shapeComponent);
	}

	public ArrayList<ShapeComponent> getComponentList() {
		return componentList;
	}

	// SELECTABLES
	public void addComponentSelection(ArrayList<ShapeComponent> componentSelection) throws Exception {
		componentSelectionList.addAll(componentSelection);
	}
	public void addComponentSelection(ShapeComponent component) throws Exception {
		componentSelectionList.add(component);
	}

	public ArrayList<ShapeComponent> getComponentSelectionList () {
		return componentSelectionList;
	}
	
	public void clearComponentSelectionList() {
		componentSelectionList.clear();
	}

	// COPYABLES
	public ArrayList<ShapeComponent> getComponentCopyBuffer() {
		return componentCopyBuffer;
	}

	public void setComponentCopyBuffer(ArrayList<ShapeComponent> copyBuffer) {
		this.componentCopyBuffer = copyBuffer;
	}

	// PASTEABLES
	public PointInt getLastPasteLocation() {
		return lastPasteLocation;
	}

	public void setLastPasteLocation(PointInt lastPasteLocation) {
		this.lastPasteLocation = lastPasteLocation;
	}

	// MOUSE 
	public void setMousePressed(PointInt point) {
		mousePressed = point; 
	}
	
	public PointInt getMousePressed() {
		return mousePressed;
	}

	public void setMouseReleased(PointInt point) throws Exception {
		mouseReleased = point; 
	}
	
	public PointInt getMouseReleased() {
		return mouseReleased;
	}

	// GROUPABLES
	public ArrayList<ShapeGroup> getShapeGroups() {
		ArrayList<ShapeComponent> list = getComponentList();
		ArrayList<ShapeGroup> listA = new ArrayList<>(); 

		listA = (ArrayList<ShapeGroup>) list
			.stream()
			.filter((c) -> c instanceof ShapeGroup)
			.map((c) -> (ShapeGroup) c)
			.collect(Collectors.toList());
		return listA;
	}

	public void addSelectionGroup(ShapeGroup group) throws Exception {
		shapeGroups.add(group);
	}
	
	public void removeSelectionGroup(ShapeGroup group) throws Exception {
		shapeGroups.remove(group);
	}

	public ArrayList<ShapeGroup> getGroupList() {
		return shapeGroups;
	}

	@Override
	public ArrayList<IShape> getShapeList() {
		ArrayList<ShapeComponent> list = getComponentList();
		ArrayList<IShape> listA = new ArrayList<>(); 

		for (ShapeComponent c : list) 
			listA.addAll(c.getShapes());

		listA.stream().distinct().collect(Collectors.toList());
		return listA;
	}
	
	public ArrayList<IShape> getShapeSelectionList() {
		ArrayList<IShape> shapeSelectionList = new ArrayList<>();
		shapeSelectionList.addAll(componentSelectionList.stream()
			.filter ((component) -> component instanceof IShape)
			.map((component) -> (IShape) component)
			.collect (Collectors.toList()));
		return shapeSelectionList;
	}

	public void addShapeGroup(ShapeGroup group) {
		ArrayList<ShapeComponent> list = new ArrayList<>();
		list.add(group);
		addComponent(list);	
	}

	public void addShapeGroup(ArrayList<ShapeGroup> components) {
		ArrayList<ShapeComponent> list = new ArrayList<>();
		list.addAll(componentSelectionList.stream()
			.map((component) -> (ShapeComponent) component)
			.collect (Collectors.toList()));

		addComponent(list);	
	}

}