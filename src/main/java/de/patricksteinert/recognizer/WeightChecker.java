package de.patricksteinert.recognizer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Weight checker is an observable checker for weight changes.
 * <p>
 * In the current development phase, it just randomly triggers a property change with random value. The process runs
 * in a separate thread.
 * The observable property name is <code>weight</code>.
 * <p>
 * Created by Patrick Steinert on 21.06.21.
 *
 * @author Patrick Steinert.
 * @since 1.0
 */
public class WeightChecker implements Runnable {

    /**
     * Property change
     */
    private PropertyChangeSupport support;

    /**
     * Default constructor.
     */
    public WeightChecker() {
        support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener for property changed.
     *
     * @param pcl change listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Removes an previously added listener.
     *
     * @param pcl change listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /**
     * Starts the weight checking.
     */
    @Override
    public void run() {
        while (true) {
            double value = Math.random() * 10000;
            try {
                Thread.sleep((long) value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            support.firePropertyChange("weight", 0, value);
        }
    }
}
