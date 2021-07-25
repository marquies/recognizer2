package de.patricksteinert.recognizer;

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

        display = new Display();

        // Fake recognition is used for development purposes.
        //recognition = new FakeRecognition();
        //recognition = new SampleRecognition();
        recognition = new YoloRecognition();

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
        cvSaveImage("/tmp/images" + File.separator + (2) + "-aa.jpg", preparedImg);

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
