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

    public List<Result> recognize(IplImage image);

}
