package de.patricksteinert.recognizer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Created by Patrick Steinert on 23.07.21.
 */
public class YoloRecognitionTest {
    @Test
    @Disabled // Just works on local machine with model in place
    public void testYoloRecognitionExecution() {
        YoloRecognition yr = new YoloRecognition();
        yr.executePythonInference();
    }
}
