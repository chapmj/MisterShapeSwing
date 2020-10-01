package model.interfaces;

import model.shape.ShapeComponent;

import java.util.List;

public interface ICanvasState {
	List<IShape> getShapeList();
	public void addComponent(List<ShapeComponent> components);
	public void removeComponent(List<ShapeComponent> components);
	public void removeComponent(ShapeComponent shapeComponent);
	public List<ShapeComponent> getComponentList();
}
