package businessLayer;
/**
    @interface Observer - interface to pe implemented by all the classed that want to
    be notified once updates are available. The updates are provided by the restaurant
    class that implements the Subject interface.
 */
public interface Observer {
    /**
        @param arg - all the Object type arguments that are updated when the method is called.
     */
    void update(Object arg[]);
}
