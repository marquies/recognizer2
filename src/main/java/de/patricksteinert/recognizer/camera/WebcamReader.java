package de.patricksteinert.recognizer.camera;

import org.bytedeco.opencv.opencv_core.IplImage;

/**
 * Interface for reading still image from a camera device.
 *
 * Created by Patrick Steinert on 30.06.21.
 * @since 1.0
 * @author Patrick Steinert
 */
public interface WebcamReader {
    IplImage readImage();
}
