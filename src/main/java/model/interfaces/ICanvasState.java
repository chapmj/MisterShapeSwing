package model.interfaces;

import java.util.ArrayList;

public interface ICanvasState {
	ArrayList<IShape> getShapeList();
	public void addComponent(ArrayList<ShapeComponent> components);
	public void removeComponent(ArrayList<ShapeComponent> components);
	public void removeComponent(ShapeComponent shapeComponent);
	public ArrayList<ShapeComponent> getComponentList();
}
