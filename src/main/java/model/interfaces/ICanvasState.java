package model.interfaces;

import model.shape.ShapeComponent;

import java.util.List;

public interface ICanvasState {
	List<IShape> getShapeList();
	void addComponent(List<ShapeComponent> components);
	void removeComponent(List<ShapeComponent> components);
	void removeComponent(ShapeComponent shapeComponent);
	List<ShapeComponent> getComponentList();
}
