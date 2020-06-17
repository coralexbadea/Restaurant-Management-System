package businessLayer;

/**
 * @interface Subject - Interface implemented by the classes that are intended to be
 * the subject/observed object in the Observer Design Pattern
 */
public interface Subject {
    /**
     * register method - used to register an observer to the set oof observers to be notified
     * @param o -  the Observer object that has to be added
     */
    void register(Observer o);

    /**
     *   used to unregister an observer to the set oof observers to be notified
     * @param o - the Observer object that has to be deleted
     */
    void unregister(Observer o);

    /**
     * notifyObserver method - used to notify the Observer objects in the set of
     * observers 
     */
    void notifyObserver();

}
