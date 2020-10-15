package view.api;

import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class MouseListenerSvc
{
    private static final Consumer<MouseListener> consumer = (listener) -> CanvasSvc.accept(listener);

    public static void accept(MouseListener mouseListener)
    {
        consumer.accept(mouseListener);
    }
}
