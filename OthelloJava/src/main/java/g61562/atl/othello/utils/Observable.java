package g61562.atl.othello.utils;

/**
 * The Observable interface represents an object that can be observed by observers.
 * Observers can register and unregister themselves to receive notifications from the observable object.
 */
public interface Observable {
    void register(Observer observer);

    void unregister(Observer observer);
}