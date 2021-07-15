package de.patricksteinert.recognizer;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Size;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.opencv.global.opencv_videoio.CAP_GSTREAMER;
import static org.opencv.core.CvType.CV_8UC3;

/**                                                            
 * Created by Patrick Steinert on 30.06.21.
 */
public class RaspberryPiWebcamReader implements WebcamReader {
    final int INTERVAL = 100;///you may use interval
//    CanvasFrame canvas = new CanvasFrame("Web Cam");

    private static final String GSTREAMER_PIPELINE = "nvarguscamerasrc ! \\'video/x-raw(memory:NVMM), width=3280, height=2464, format=(string)NV12, framerate=21/1\\' ! nvvidconv flip-method=0 ! \\'video/x-raw, width=960, height=616, format=(string)BGRx\\' ! videoconvert ! \\'video/x-raw, format=(string)BGR\\' ! appsink wait-on-eos=false max-buffers=1 drop=True";

    public RaspberryPiWebcamReader() {

//        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public IplImage readImage() {
        new File("images").mkdir();
        //BytePointer ptr = new BytePointer(GSTREAMER_PIPELINE);
	System.out.println(GSTREAMER_PIPELINE);
        VideoCapture vc = new VideoCapture(GSTREAMER_PIPELINE, CAP_GSTREAMER);
        System.out.println("Exception Mode: " + vc.getExceptionMode());
        vc.setExceptionMode(true);
        System.out.println("Exception Mode: " + vc.getExceptionMode());
        System.out.println("Backend Name: " + vc.getBackendName());
        if(!vc.isOpened()) {
            System.out.println("VideoCapture not opened!");
        }
        Mat vcimg = new Mat(new Size(960, 616), CV_8UC3);
        boolean ok = vc.read(vcimg);
        if (ok) {
            System.out.println("Read OK");
        } 

        //imwrite("images" + File.separator + (0) + "-ab.jpg", vcimg);



        FrameGrabber grabber = new OpenCVFrameGrabber(GSTREAMER_PIPELINE); // 1 for next camera
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
