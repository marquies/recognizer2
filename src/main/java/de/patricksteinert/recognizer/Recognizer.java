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
 *
 * Recognizer initializes the components of the application.
 *
 * @author Patrick Steinert
 * @since 1.0
 * Created by Patrick Steinert on 21.06.21.
 */
public class Recognizer implements PropertyChangeListener {

    private WeightChecker w;
    private WebcamReader webcamReader;
    private Preprocessor preprocessor;
    private Recognition recognition;
    private Display display;


    public Recognizer() {

        display = new Display();
        
//        recognition = new FakeRecognition();
        recognition = new SampleRecognition();

        preprocessor = new Preprocessor();

        if (SystemUtils.IS_OS_LINUX) {
            System.out.println("Detected Linux os, setup GSTREAMER based webcam");
            webcamReader = new RaspberryPiWebcamReader();
        } else {
            System.out.println("Detected non Linux os, use default webcam");
            webcamReader = new GenericWebcamReader();

        }


        w = new WeightChecker();
        w.addPropertyChangeListener(this);
        w.run();


    }

    public static void main(String[] args) {
        Recognizer r = new Recognizer();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("weight")) {
            System.out.printf(String.valueOf(evt.getNewValue()));
        }

        IplImage img = webcamReader.readImage();
        IplImage preparedImg = preprocessor.preprocess(img);

        cvSaveImage("/tmp/images" + File.separator + (2) + "-aa.jpg", preparedImg);

        List<Result> results = recognition.recognize(preparedImg);
        for (Result result : results) {
            System.out.println("Class '" + result.getObjectClass() + "' confidence '" + result.getConfidence() + "'");
        }

        if (!results.isEmpty())
            display.showText(results.get(0).getObjectClass());

        


    }
}
