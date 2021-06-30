package de.patricksteinert.recognizer;

/**
 * Created by Patrick Steinert on 21.06.21.
 */
public class Result {
    private String objectClass;
    private double confidence;

    public Result(String objectClass, double confidence) {
        this.objectClass = objectClass;
        this.confidence = confidence;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public double getConfidence() {
        return confidence;
    }
}
