package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;

import java.util.List;

/**
 * The interface should be used for different recognition systems.
 *
 * @author Patrick Steinert
 * @since 1.0
 * Created by Patrick Steinert on 21.06.21.
 */
public interface Recognition {

    /**
     * Recognition class to implement. Should do the image recognition on the image and return the found classes.
     *
     * @param image image to process.
     * @return List of Result objects.
     */
    public List<Result> recognize(IplImage image);

}
