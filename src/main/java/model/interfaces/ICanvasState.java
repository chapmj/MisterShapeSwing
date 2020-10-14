package model.interfaces;

import java.util.List;

public interface ICanvasState {
	List<IShape> getShapeList();
	void addComponent(List<IShape> components);
	void removeComponent(List<IShape> components);
	void removeComponent(IShape shapeComponent);
	List<IShape> getComponentList();
}
