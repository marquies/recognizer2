package de.patricksteinert.recognizer;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;

/**
 * Created by Patrick Steinert on 21.06.21.
 */
public class GenericWebcamReader implements WebcamReader {
    final int INTERVAL = 100;///you may use interval
//    CanvasFrame canvas = new CanvasFrame("Web Cam");

    public GenericWebcamReader() {

//        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public IplImage readImage() {
        new File("images").mkdir();

        FrameGrabber grabber = new OpenCVFrameGrabber(1); // 1 for next camera
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        IplImage img;
        int i = 0;
        try {
            grabber.start();

            Frame frame = grabber.grab();

            img = converter.convert(frame);

            //the grabbed frame will be flipped, re-flip to make it right
            cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise

            //save
            cvSaveImage("images" + File.separator + (0) + "-aa.jpg", img);

            //canvas.showImage(converter.convert(img));

            return img;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
