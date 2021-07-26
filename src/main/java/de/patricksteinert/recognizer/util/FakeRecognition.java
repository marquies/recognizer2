package de.patricksteinert.recognizer.util;

import de.patricksteinert.recognizer.Recognition;
import de.patricksteinert.recognizer.Result;
import org.bytedeco.opencv.opencv_core.IplImage;

import java.util.ArrayList;
import java.util.List;

/**
 * FakeRecognition can be used for testing purposes.
 *
 * @author Patrick Steinert
 * @sinve 1.0
 * Created by Patrick Steinert on 21.06.21.
 */
public class FakeRecognition implements Recognition {

    /**
     * Returns a single static value pair.
     * <p>
     * object class: unclassified
     * confidence: 99.9
     *
     * @param image input image will be ignored, can be null.
     * @return a single static value pair.
     */
    @Override
    public List<Result> recognize(IplImage image) {
        ArrayList<Result> results = new ArrayList<>();
        results.add(new Result("unclassified", 99.9));
        return results;
    }
}
