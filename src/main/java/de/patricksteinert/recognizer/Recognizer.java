package de.patricksteinert.recognizer;

import de.patricksteinert.recognizer.camera.GenericWebcamReader;
import de.patricksteinert.recognizer.camera.RaspberryPiWebcamReader;
import de.patricksteinert.recognizer.camera.WebcamReader;
import de.patricksteinert.recognizer.util.FakeRecognition;
import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.opencv.opencv_core.IplImage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;

/**
 * Main class of the application.
 * <p>
 * Recognizer initializes the components of the application.
 *
 * @author Patrick Steinert
 * @since 1.0
 * Created by Patrick Steinert on 21.06.21.
 */
public class Recognizer implements PropertyChangeListener {


    public static final String RECOGNIZER_METHOD_ENV = "RECOGNIZER_METHOD";
    public static final String TMP_IMAGE_FOLDER = "/tmp/images";
    private WeightChecker weightChecker;
    private WebcamReader webcamReader;
    private Preprocessor preprocessor;
    private Recognition recognition;
    private Display display;


    /**
     * The constructor initializes all components to start the application. Nothing more is needed to run the
     * application.
     */
    public Recognizer() {

        // Initialization of the application components.

        File f = new File(TMP_IMAGE_FOLDER);
        if (!f.exists()) {
            f.mkdirs();
        }

        display = new Display();

        String method = null;
        if (System.getenv(RECOGNIZER_METHOD_ENV) != null) {
            method = System.getenv(RECOGNIZER_METHOD_ENV);
            System.out.println("Method '" + method + "' has been initialized from environment variable.");
        } else {
            method = "fake";
            System.out.println("No environment variable " + RECOGNIZER_METHOD_ENV + "  - " +
                    "Method '" + method
                    + "' has been used automatically. You can set the variable with values fake, ssd_mobilenet or yolov3");
        }

        switch (method) {
            case "fake":
                recognition = new FakeRecognition();
                break;
            case "ssd_mobilenet":
                recognition = new SampleRecognition();
                break;
            case "yolov3":
                recognition = new YoloRecognition();
                break;
            default:
                System.out.println("Unknown recognition method '" + method + "'");
                System.exit(9);
        }


        preprocessor = new Preprocessor();

        // On the embedded devices, the Raspberry Pi Webcam should be used.
        if (SystemUtils.IS_OS_LINUX) {
            System.out.println("Detected Linux os, setup GSTREAMER based webcam");
            webcamReader = new RaspberryPiWebcamReader();
        } else {
            System.out.println("Detected non Linux os, use default webcam");
            webcamReader = new GenericWebcamReader();

        }

        weightChecker = new WeightChecker();
        weightChecker.addPropertyChangeListener(this);
        weightChecker.run();


    }

    /**
     * Main class of the application.
     *
     * @param args No arguments necessary.
     */
    public static void main(String[] args) {
        Recognizer r = new Recognizer();
    }

    /**
     * Property change listener for weight changes.
     *
     * @param evt event data will include the new weight measured by the WeightChecker.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("weight")) {
            System.out.printf(String.valueOf(evt.getNewValue()));
        }

        // 1. Read an image from the camera
        IplImage img = webcamReader.readImage();

        // 2. Proprocess the image from the camera
        IplImage preparedImg = preprocessor.preprocess(img);

        // Store for validation purposes
        cvSaveImage(TMP_IMAGE_FOLDER + File.separator + (2) + "-aa.jpg", preparedImg);

        // 3. Run object recognition
        List<Result> results = recognition.recognize(preparedImg);
        for (Result result : results) {
            System.out.println("Class '" + result.getObjectClass() + "' confidence '" + result.getConfidence() + "'");
        }

        // 4. Present the recognition result on the display
        if (!results.isEmpty())
            display.showText(results.get(0).getObjectClass());


    }
}
