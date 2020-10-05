package model.interfaces;

import java.util.List;

/* OBSERVER PATTERN
 */
public interface IObservableSubject{
	// Citation: Head First Design Patterns, pg 58.
    void registerObserver(IObservable obs);
	void deregisterObserver(IObservable obs);
	List<IObservable> listObservers();
	void notifyObservers() throws Exception;
}
