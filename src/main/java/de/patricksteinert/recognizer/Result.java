package de.patricksteinert.recognizer;

/**
 * Result data object for recognized objects.
 * <p>
 * Created by Patrick Steinert on 21.06.21.
 *
 * @author Patrick Steinert
 * @since 1.0
 */
public class Result {
    /**
     * The recognized class (aka label).
     */
    private final String objectClass;

    /**
     * The confidence level in double precision.
     */
    private final double confidence;

    /**
     * Default constructor for immuntable
     *
     * @param objectClass
     * @param confidence
     */
    public Result(String objectClass, double confidence) {
        this.objectClass = objectClass;
        this.confidence = confidence;
    }

    /**
     * Get the object class.
     *
     * @return object class as String.
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * Get the confidence level as double.
     *
     * @return confidence level as double.
     */
    public double getConfidence() {
        return confidence;
    }
}
