package model.persistence;

import java.util.ArrayList;

import model.interfaces.IObservableObserver;
import model.interfaces.IObservableSubject;

// Store canvasState observers
public class CanvasStateSubject implements IObservableSubject {

	private ArrayList<IObservableObserver> observers = new ArrayList<>();
	
	public CanvasStateSubject() {
	
	}

	@Override
	public void registerObserver(IObservableObserver obs) {
		observers.add((CanvasStateObserver) obs);	
	}

	@Override
	public void deregisterObserver(IObservableObserver obs) {
		observers.remove(obs);
	}

	@Override
	public ArrayList<IObservableObserver> listObservers() {
		return this.observers;	
	}

	@Override
	public void notifyObservers() throws Exception {
		for (IObservableObserver observer : observers)
			observer.update();
	}

}
