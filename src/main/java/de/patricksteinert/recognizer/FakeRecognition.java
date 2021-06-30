package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick Steinert on 21.06.21.
 */
public class FakeRecognition implements Recognition {
    @Override
    public List<Result> recognize(IplImage image) {
        ArrayList<Result> results = new ArrayList<>();
        results.add(new Result("unclassified", 99.9));
        return results;
    }
}
