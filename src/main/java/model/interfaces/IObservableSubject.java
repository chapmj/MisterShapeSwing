package model.interfaces;

import java.util.ArrayList;
/* OBSERVER PATTERN
 */
public interface IObservableSubject{
	// Citation: Head First Design Patterns, pg 58.
	public void registerObserver(IObservableObserver obs);
	public void deregisterObserver(IObservableObserver obs);
	public ArrayList<IObservableObserver> listObservers();
	public void notifyObservers() throws Exception;
}
