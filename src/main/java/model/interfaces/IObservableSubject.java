package model.interfaces;

import java.util.List;

/* OBSERVER PATTERN
 */
public interface IObservableSubject{
	// Citation: Head First Design Patterns, pg 58.
	public void registerObserver(IObservable obs);
	public void deregisterObserver(IObservable obs);
	public List<IObservable> listObservers();
	public void notifyObservers() throws Exception;
}
