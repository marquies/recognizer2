package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;

import java.util.List;

/**
 * Created by Patrick Steinert on 21.06.21.
 */
public interface Recognition {

    public List<Result> recognize(IplImage image);

}
