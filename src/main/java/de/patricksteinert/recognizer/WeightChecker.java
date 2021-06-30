package de.patricksteinert.recognizer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Patrick Steinert on 21.06.21.
 */
public class WeightChecker implements Runnable {

    private PropertyChangeSupport support;

    public WeightChecker() {
        support = new PropertyChangeSupport(this);

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void run() {
        while(true) {
            double foo = Math.random() * 10000;
            try {
                Thread.sleep((long) foo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            support.firePropertyChange("weight", 0, foo);
        }
    }
}
