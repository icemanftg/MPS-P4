package ro.mps.gui.screens;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 05.01.2013
 * Time: 21:39
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
