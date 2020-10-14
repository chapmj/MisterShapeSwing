package model.persistence;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.IObservable;
import model.interfaces.IObservableSubject;

// Store canvasState observers
public class CanvasStateSubject implements IObservableSubject {

	private final List<IObservable> observers;
	
	public CanvasStateSubject(List<IObservable> observers)
	{
		this.observers = observers;
	}

	@Override
	public void registerObserver(IObservable observer) {
		observers.add(observer);
	}

	@Override
	public void deregisterObserver(IObservable obs) {
		observers.remove(obs);
	}

	@Override
	public List<IObservable> listObservers() {
		return this.observers;	
	}

	@Override
	public void notifyObservers() throws Exception {
		observers.stream().forEach(IObservable::update);
	}

}
