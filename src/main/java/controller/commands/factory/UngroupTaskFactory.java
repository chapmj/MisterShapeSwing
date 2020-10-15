package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.UngroupTask;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UngroupTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<IShape>> selection;
    Predicate<IShape> isShapeGroup = component -> component instanceof ShapeGroup;
    Predicate<IShape> isShape = shapeComponent -> shapeComponent instanceof IShape;
    Function<List<IShape>, Stream<ShapeComponent>> toComponentStream = shapes -> shapes.stream().map(shape -> (ShapeComponent)shape);
    Function<Optional<IShape>, Stream<ShapeComponent>> optionToStream = (comp) -> Stream.of(comp.get()).map((shape) -> (ShapeComponent) shape);

    public UngroupTaskFactory(Supplier<List<IShape>> selection)
    {
        this.selection = selection;
    }

    @Override
    public AbstractControllerTask createTask()
    {

        var shapes = selection.get();

        /* populate shapes into two categories: ungroupedShapes (shapeleafs), groups (shapeGroup composite) */
        var groups = shapes.stream().filter(isShapeGroup).collect(Collectors.toList());
        var ungroupedShapes = shapes.stream().filter(isShape).collect(Collectors.toList());

        /* Flatten all shapes in a group into just a shape collection */
        List<IShape> groupedShapes = groups.stream()
                .map(Optional::of)
                .filter(Optional::isPresent)
                .flatMap(optionToStream)
                .map(ShapeComponent::getShapes)
                .flatMap(toComponentStream)
                .collect(Collectors.toList());

        return new UngroupTask(groups, ungroupedShapes, groupedShapes);
    }
}
