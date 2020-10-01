package model.persistence;

import model.PointInt;
import model.shape.ShapeGroup;
import model.interfaces.ICanvasState;
import model.interfaces.IShape;
import model.shape.ShapeComponent;

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
	private List<ShapeGroup> shapeGroups;
	private List<ShapeComponent> componentSelectionList;
	private List<ShapeComponent> componentList;
	private List<ShapeComponent> componentCopyBuffer;
	CanvasStateSubject canvasStateSubject;

	public CanvasState() {
		this.mousePressedCoord = new PointInt();
		this.mouseReleasedCoord = new PointInt();
		this.shapeGroups = new ArrayList<>();
		this.componentSelectionList = new ArrayList<>();
		this.componentList = new ArrayList<>();
		this.componentCopyBuffer  = new ArrayList<>();
		this.lastPasteLocation = new PointInt(0,0);
		this.canvasStateSubject = new CanvasStateSubject();
	}

	// DRAWABLES

	public void addComponent(ShapeComponent component)
	{
		componentList.add(component);
	}

	public void addComponent(List<ShapeComponent> components)
	{
		componentList.addAll(components);
	}

	public void removeComponent(List<ShapeComponent> components) {
		componentList.removeAll(components);
	}

	public void removeComponent(ShapeComponent shapeComponent) {
		componentList.remove(shapeComponent);
	}

	public List<ShapeComponent> getComponentList()
	{
		return componentList;
	}

	// SELECTABLES
	public void addComponentSelection(List<ShapeComponent> componentSelection)
	{
		componentSelectionList.addAll(componentSelection);
	}
	public void addComponentSelection(ShapeComponent component)
	{
		componentSelectionList.add(component);
	}

	public List<ShapeComponent> getComponentSelectionList ()
	{
		return componentSelectionList;
	}
	
	public void clearComponentSelectionList()
	{
		componentSelectionList.clear();
	}

	// COPYABLES
	public List<ShapeComponent> getComponentCopyBuffer()
	{
		return componentCopyBuffer;
	}

	public void setComponentCopyBuffer(List<ShapeComponent> copyBuffer)
	{
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
	public void setMousePressed(PointInt point)
	{
		mousePressedCoord = point;
	}
	
	public PointInt getMousePressed()
	{
		return mousePressedCoord;
	}

	public void setMouseReleased(PointInt point)
	{
		mouseReleasedCoord = point;
	}
	
	public PointInt getMouseReleased()
	{
		return mouseReleasedCoord;
	}

	// GROUPABLES
	public List<ShapeGroup> getShapeGroups() {
		return this.componentList.stream()
				.filter((c) -> c instanceof ShapeGroup)
				.map((c) -> (ShapeGroup) c)
				.collect(Collectors.toList());
	}

	public void addSelectionGroup(ShapeGroup group) {
		shapeGroups.add(group);
	}
	
	public void removeSelectionGroup(ShapeGroup group) {
		shapeGroups.remove(group);
	}

	public List<ShapeGroup> getGroupList() {
		return shapeGroups;
	}

	@Override
	public List<IShape> getShapeList() {
		return componentList.stream()
				.flatMap((shapeComponent) -> Stream.of(shapeComponent.getShapes()))
				.flatMap(Collection::stream)
                .distinct()
				.collect(Collectors.toList());

	}
	
	public List<IShape> getShapeSelectionList() {
		return Stream.of(componentSelectionList)
				.filter ((component) -> component instanceof IShape)
				.map((component) -> (IShape) component)
				.collect (Collectors.toList());

	}

	public void addShapeGroup(ShapeGroup group) {
		ArrayList<ShapeComponent> list = new ArrayList<>();
		list.add(group);
		addComponent(list);	
	}
}