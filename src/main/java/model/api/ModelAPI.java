package model.api;

import model.PointInt;
import model.interfaces.IShape;
import model.persistence.CanvasState;
import model.persistence.ModelState;
import model.shape.Shape;
import model.shape.ShapeComponent;
import model.shape.ShapeGroup;

import java.util.List;

//one interface for controller and view to interact with
public class ModelAPI
{
        public static void addShapeGroup(ShapeGroup shapeGroup)
        {
                ModelState.getCanvasState().addShapeGroup(shapeGroup);

        }

        public static void addShapes(List<ShapeComponent> shapes)
        {
                ModelState.getCanvasState().addComponent(shapes);
        }


        public static void removeShapes(List<ShapeComponent> shapeComponents)
        {
                ModelState.getCanvasState().removeComponent(shapeComponents);

        }

        public static void addComponentSelection(ShapeGroup group)
        {
                //returns a list of components that are in the current selection.
                //controller calls this to store a list of shapes (Copy Task) then calls puts list of shapes in copybuffer
            ModelState.getCanvasState().addComponentSelection(group);

        }
        public static void addComponentSelection(List<ShapeComponent> selection)
        {
                ModelState.getCanvasState().addComponentSelection(selection);
        }

        public static void transferSelectionToBuffer()
        {
                var canvasState = ModelState.getCanvasState();

                var selection = canvasState.getComponentSelectionList();
                canvasState.setComponentCopyBuffer(selection);
        }

        public static void clearSelection()
        {
                ModelState.getCanvasState().clearComponentSelectionList();
        }

        public static List<ShapeComponent> getComponentBuffer()
        {
                return ModelState.getCanvasState().getComponentCopyBuffer();
        }

        //paste
        public static PointInt getPasteLocation()
        {
            return ModelState.getCanvasState().getLastPasteLocation();

        }

        public static void setPasteLocation(PointInt point)
        {

        }

        public static void setPasteLocation(int offsetX, int offSetY)
        {

        }

        public static void setShapeLocation(ShapeComponent shapeComponent, int x, int y)
        {
                shapeComponent.setAnchor(new PointInt(x, y));
                var z = (IShape) shapeComponent;

        }

        public static void commit()
        {
                ModelState.refresh();
        }

        public static void removeShape(ShapeComponent shape)
        {
                ModelState.getCanvasState().removeComponent(shape);
        }

        public static void addShape(ShapeComponent shape)
        {
                ModelState.getCanvasState().addComponent(shape);
        }
}
