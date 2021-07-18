package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

/**
 * Created by Patrick Steinert on 30.06.21.
 */
public class RaspberryPiWebcamReader implements WebcamReader {
    private static final String GSTREAMER_PIPELINE = "nvarguscamerasrc ! \\'video/x-raw(memory:NVMM), width=3280, height=2464, format=(string)NV12, framerate=21/1\\' ! nvvidconv flip-method=0 ! \\'video/x-raw, width=960, height=616, format=(string)BGRx\\' ! videoconvert ! \\'video/x-raw, format=(string)BGR\\' ! appsink wait-on-eos=false max-buffers=1 drop=True";
    //    CanvasFrame canvas = new CanvasFrame("Web Cam");
    final int INTERVAL = 100;///you may use interval

    public RaspberryPiWebcamReader() {

//        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public IplImage readImage() {
//        new File("images").mkdir();
//        //BytePointer ptr = new BytePointer(GSTREAMER_PIPELINE);
//        System.out.println(GSTREAMER_PIPELINE);
//        VideoCapture vc = new VideoCapture(GSTREAMER_PIPELINE, CAP_GSTREAMER);
//        System.out.println("Exception Mode: " + vc.getExceptionMode());
//        vc.setExceptionMode(true);
//        System.out.println("Exception Mode: " + vc.getExceptionMode());
//
//        if (!vc.isOpened()) {
//            System.out.println("VideoCapture not opened!");
//        }
//        boolean retVal = vc.open(GSTREAMER_PIPELINE, CAP_GSTREAMER);
//        System.out.println("Open Return Value: " + retVal);
//        System.out.println("Backend Name: " + vc.getBackendName());
//
//        Mat vcimg = new Mat(new Size(960, 616), CV_8UC3);
//        boolean ok = vc.read(vcimg);
//        if (ok) {
//            System.out.println("Read OK");
//        }
//
//        imwrite("images" + File.separator + (0) + "-ab.jpg", vcimg);
//
//
//        FrameGrabber grabber = new OpenCVFrameGrabber(GSTREAMER_PIPELINE); // 1 for next camera
//        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//        IplImage img;
//        int i = 0;
//        try {
//            grabber.start();
//
//            Frame frame = grabber.grab();
//
//            img = converter.convert(frame);
//
//            //the grabbed frame will be flipped, re-flip to make it right
//            cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
//
//            //save
//            cvSaveImage("images" + File.separator + (0) + "-aa.jpg", img);
//
//            //canvas.showImage(converter.convert(img));
//
//            return img;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            pythonExecution();
            Mat image = imread("/tmp/image.jpg");
            IplImage img = new IplImage(image);
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void pythonExecution() throws Exception {
        String s = null;

        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec("listcams.py");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
