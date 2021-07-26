package de.patricksteinert.recognizer;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.addWeighted;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

/**
 * The preprocessor optimizes an image with for object recognition with simple image processing.
 * <p>
 * Enhancements:
 * <ol>
 *     <li>Gaussian unsharpening.</li>
 * </ol>
 *
 * @author Patrick Steinert
 * Created by Patrick Steinert on 21.06.21.
 * @since 1.0
 */
public class Preprocessor {


    /**
     * Preprocessing.
     *
     * @param image image to process.
     * @return processed image.
     */
    public IplImage preprocess(IplImage image) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();


        Mat src = new Mat(image);
        Mat dest = new Mat(src.rows(), src.cols(), src.type());

        opencv_imgproc.GaussianBlur(src, dest, new Size(0, 0), 10);
        addWeighted(src, 1.5, dest, -0.5, 0, dest);


        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        Frame frame = matConverter.convert(dest);

        IplImage output = iplConverter.convert(frame);

        IplImage result = output.clone();
        output.release();


        imwrite("/tmp/images" + File.separator + (1) + "-aa.jpg", dest);
        //cvSaveImage("images" + File.separator + (1) + "-aa.jpg", output);
        // Define output image

        // Construct sharpening kernel, oll unassigned values are 0
        //Mat kernel = new Mat(3, 3, CV_32F, new Scalar(0));

//        Imgproc.GaussianBlur(src, dest, new Size(0,0), 10);
//        Core.addWeighted(src, 1.5, dest, -0.5, 0, dest);
//
//        cvSaveImage("images" + File.separator + (1) + "-aa.jpg", image);

        return result;

    }
}
