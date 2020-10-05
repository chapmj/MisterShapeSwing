package model.persistence;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.IObservable;
import model.interfaces.IObservableSubject;

// Mailbox for mouse observers
public class MouseReleaseSubject implements IObservableSubject {

	private List<IObservable> observers = new ArrayList<>();
	
	public MouseReleaseSubject() {
	
	}

	@Override
	public List<IObservable> listObservers() {
		return this.observers;	
	}

	@Override
	public void notifyObservers() throws Exception {
		for (IObservable observer : observers)
			observer.update();
	}

	@Override
	public void registerObserver(IObservable obs) {
		observers.add(obs);
	}

	@Override
	public void deregisterObserver(IObservable obs) {
		observers.remove(obs);
	}

}
