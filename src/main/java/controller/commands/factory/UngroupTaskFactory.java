package controller.commands.factory;

import controller.commands.AbstractControllerTask;
import controller.commands.UngroupTask;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UngroupTaskFactory extends AbstractTaskFactory
{
    private final Supplier<List<ShapeComponent>> selectionSupplier;

    public UngroupTaskFactory(Supplier<List<ShapeComponent>> selectionSupplier)
    {
        this.selectionSupplier = selectionSupplier;
    }

    @Override
    public AbstractControllerTask createTask()
    {
        Predicate<ShapeComponent> isShapeGroup = component -> component instanceof ShapeGroup;
        Predicate<ShapeComponent> isShape = shapeComponent -> shapeComponent instanceof IShape;
        Function<List<IShape>, Stream<ShapeComponent>> toComponentStream = shapes -> shapes.stream().map(shape -> (ShapeComponent)shape);
        Function<Optional<ShapeComponent>, Stream<ShapeComponent>> optionToStream = (comp)->Stream.of(comp.get());

        var shapes = selectionSupplier.get();

        /* populate shapes into two categories: ungroupedShapes (shapeleafs), groups (shapeGroup composite) */
        var groups = shapes.stream().filter(isShapeGroup).collect(Collectors.toList());
        var ungroupedShapes = shapes.stream().filter(isShape).collect(Collectors.toList());

        /* Flatten all shapes in a group into just a shape collection */
        var groupedShapes = groups.stream()
                .map(Optional::of)
                .filter(Optional::isPresent)
                .flatMap(optionToStream)
                .map(ShapeComponent::getShapes)
                .flatMap(toComponentStream)
                .collect(Collectors.toList());

        return new UngroupTask(groups, ungroupedShapes, groupedShapes);
    }
}
