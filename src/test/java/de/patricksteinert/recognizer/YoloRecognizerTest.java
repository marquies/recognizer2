package de.patricksteinert.recognizer;

import de.patricksteinert.recognizer.yolov3.Yolov3;
import org.junit.jupiter.api.Test;

/**
 * Created by Patrick Steinert on 23.07.21.
 */
public class YoloRecognizerTest {

    @Test
    public void testYolov3ModelLoadAndPredict() {
        Yolov3 y = new Yolov3();
        //y.inference("./images/2-aa.jpg");
    }
}
