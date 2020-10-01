package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.shape.ShapeGroup;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import view.drawstrategy.*;

/* Draw shape data to canvas.
 * Shapes are appended to the shape drawer object prior to a call to draw them.
 * BUILDER PATTERN
 */
public class ShapeDrawer {
	private List<IShape> shapeDrawerShapes = new ArrayList<>();
	private List<ShapeGroup> groups = new ArrayList<>();
	
	public ShapeDrawer()
	{ }

	private DrawStrategy inferDrawStrategy(ShapeComponent component)
	{
		switch (component.getType())
		{
			case RECTANGLE:
				return new DrawStrategyRectangle(component);
			case ELLIPSE:
				return new DrawStrategyEllipse(component);
			case TRIANGLE:
				return new DrawStrategyTriangle(component);
			case INVISIBLE_RECT:
				return new DrawStrategyInvisibleRect(component);
			default:
				return new DrawStrategyRectangle(component);
		}
	}

	// Divide ShapeComponents into IShapes and Shapegroups, then add all shapes to drawer.
	public void add(List<ShapeComponent> component) {
		// Add shapes to shapeDrawer pending
		component.stream()
			.filter(comp -> comp instanceof IShape)
			.map(s -> (IShape) s)
			.forEach(shapeDrawerShapes::add);
		
		// Store Groups
		component.stream()
			.filter(comp -> comp instanceof ShapeGroup)
			.map(g -> (ShapeGroup) g)
			.forEach(groups::add);
	
		// Store shapes and add to shapeDrawer pending
		component.stream()
			.filter(comp -> comp instanceof ShapeGroup)
			.map(g -> (ShapeGroup) g)
			.flatMap(c -> Stream.of(splitGroup(c)))
			.forEach(shapeDrawerShapes::addAll);
	}

	// Take a ShapeComponent and return all of the shapes in it.
	private List<IShape> splitGroup(ShapeComponent component) {
		var shapes = new ArrayList<IShape>();

		if (component instanceof ShapeGroup) {
			shapes.addAll(component.getShapes());
		}

		return shapes;
	}

	// Builder execute
	public void draw()
	{

		shapeDrawerShapes.stream()
				.map((shape) -> (ShapeComponent)shape)
				.map(this::inferDrawStrategy)
				.forEach(DrawStrategy::draw);

		groups.stream()
				.map(this::inferDrawStrategy)
				.forEach(DrawStrategy::draw);
	}

	// Builder append
	public void add(ShapeComponent shape) {
		var component = new ArrayList<ShapeComponent>();
		component.add(shape);
		add(component);
	}
}