package model.persistence;

import model.PointInt;
import model.interfaces.ICanvasState;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Stores data for interactions with the canvas.
 */
public class CanvasState implements ICanvasState
{
	private PointInt lastPasteLocation;
	private PointInt mousePressedCoord;
	private PointInt mouseReleasedCoord;
	private final List<ShapeGroup> shapeGroups;
	private final List<IShape> componentSelectionList;
	private final List<IShape> componentList;
	private List<IShape> componentCopyBuffer;
	final CanvasStateSubject canvasStateSubject;

	public CanvasState() {
		this.mousePressedCoord = new PointInt(0,0);
		this.mouseReleasedCoord = new PointInt(0,0);
		this.shapeGroups = new ArrayList<>();
		this.componentSelectionList = new ArrayList<>();
		this.componentList = new ArrayList<>();
		this.componentCopyBuffer  = new ArrayList<>();
		this.lastPasteLocation = new PointInt(0,0);
		this.canvasStateSubject = new CanvasStateSubject(new ArrayList<>());
	}

	// DRAWABLES get, add, remove

	public void addComponent(IShape component)
	{
		componentList.add(component);
	}

	public void addComponent(List<IShape> components)
	{
		componentList.addAll(components);
	}

	public void removeComponent(List<IShape> components) {
		componentList.removeAll(components);
	}

	public void removeComponent(IShape shapeComponent) {
		componentList.remove(shapeComponent);
	}

	public List<IShape> getComponentList()
	{
		return componentList;
	}


	// SELECTABLES add, get, clear
	public void addComponentSelection(List<IShape> componentSelection)
	{
		componentSelectionList.addAll(componentSelection);
	}
	public void addComponentSelection(ShapeComponent component)
	{
		componentSelectionList.add(component);
	}

	public List<IShape> getComponentSelectionList ()
	{
		return componentSelectionList;
	}
	
	public void clearComponentSelectionList()
	{
		componentSelectionList.clear();
	}


	// COPYABLES  //get and set
	public List<IShape> getComponentCopyBuffer()
	{
		return componentCopyBuffer;
	}

	public void setComponentCopyBuffer(List<IShape> copyBuffer)
	{
		this.componentCopyBuffer = copyBuffer;
	}


	// PASTEABLES //get and set
	public PointInt getLastPasteLocation() {
		return lastPasteLocation;
	}

	public void setLastPasteLocation(PointInt lastPasteLocation) {
		this.lastPasteLocation = lastPasteLocation;
	}


	// MOUSE  //get set
	public void setMousePressed(PointInt point)
	{
		mousePressedCoord = point;
	}
	
	public PointInt getMousePressed()
	{
		return mousePressedCoord;
	}

	//get set
	public void setMouseReleased(PointInt point)
	{
		mouseReleasedCoord = point;
	}
	
	public PointInt getMouseReleased()
	{
		return mouseReleasedCoord;
	}

	// GROUPABLES  //get
	public List<ShapeGroup> getGroupList() {
		return shapeGroups;
	}

	@Override
	public List<IShape> getShapeList() {
		return componentList.stream()
				.map((shape) -> (ShapeComponent) shape)
				.flatMap((shapeComponent) -> Stream.of(shapeComponent.getShapes()))
				.flatMap(Collection::stream)
                .distinct()
				.collect(Collectors.toList());
	}

	public void addShapeGroup(ShapeGroup group) {
		ArrayList<IShape> list = new ArrayList<>();
		list.add(group);
		addComponent(list);	
	}
}