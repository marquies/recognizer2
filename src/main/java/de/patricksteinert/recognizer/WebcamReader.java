package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;

/**
 * Created by Patrick Steinert on 30.06.21.
 */
public interface WebcamReader {
    IplImage readImage();
}
