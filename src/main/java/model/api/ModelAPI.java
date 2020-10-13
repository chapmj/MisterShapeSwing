package model.api;

import controller.interfaces.IControllerTask;
import model.PointInt;
import model.interfaces.IShape;
import model.persistence.CanvasStateObserver;
import model.persistence.ModelState;
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

        public static void addShapes(List<IShape> shapes)
        {
                ModelState.getCanvasState().addComponent(shapes);
        }


        public static void removeShapes(List<IShape> shapeComponents)
        {
                ModelState.getCanvasState().removeComponent(shapeComponents);
        }

        public static List<IShape> getSelection()
        {
                return ModelState.getShapeComponentSelectionList();
        }

        public static void addComponentToSelection(ShapeGroup group)
        {
                //returns a list of components that are in the current selection.
                //controller calls this to store a list of shapes (Copy Task) then calls puts list of shapes in copybuffer
                ModelState.getCanvasState().addComponentSelection(group);
        }

        public static void addComponentToSelection(List<IShape> selection)
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

        public static List<IShape> getComponentBuffer()
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

        public static void setShapeLocation(IShape shapeComponent, int x, int y)
        {
                ((ShapeComponent)shapeComponent).setAnchor(new PointInt(x, y));
        }

        public static void setShapeLocation(IShape shapeComponent, PointInt point)
        {
                ((ShapeComponent)shapeComponent).setAnchor(point);
        }

        public static void commit()
        {
                ModelState.refresh();
        }

        public static void removeShape(ShapeComponent shape)
        {
                ModelState.getCanvasState().removeComponent(shape);
        }

        public static void addShape(IShape shape)
        {
                ModelState.getCanvasState().addComponent(shape);
        }

        public static List<IShape> getComponents()
        {
                return ModelState.getCanvasState().getComponentList();
        }

        public static void registerCanvasStateSubscriber(IControllerTask command)
        {
                var obs = new CanvasStateObserver(command);
                ModelState.getCanvasStateSubject().registerObserver(obs);
        }

        public static void notifyCanvasObservers()
        {
                try
                {
                        ModelState.getCanvasStateSubject().notifyObservers();
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }

        public static void putComponentBuffer(List<IShape> shapeComponents)
        {
                ModelState.getCanvasState().setComponentCopyBuffer(shapeComponents);
        }
}
