package model.persistence;

import java.util.ArrayList;

import model.interfaces.IObservableObserver;
import model.interfaces.IObservableSubject;

// Mailbox for mouse observers
public class MouseReleaseSubject implements IObservableSubject {

	private ArrayList<IObservableObserver> observers = new ArrayList<>();
	
	public MouseReleaseSubject() {
	
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

	@Override
	public void registerObserver(IObservableObserver obs) {
		observers.add((MouseReleaseObserver) obs);	
	}

	@Override
	public void deregisterObserver(IObservableObserver obs) {
		observers.remove(obs);
	}

}
