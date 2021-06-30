package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;
import org.opencv.core.Mat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;

/**
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
        
        recognition = new FakeRecognition();

        preprocessor = new Preprocessor();
        webcamReader = new WebcamReader();
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

        cvSaveImage("images" + File.separator + (2) + "-aa.jpg", preparedImg);

        List<Result> results = recognition.recognize(preparedImg);
        for (Result result : results) {
            System.out.println("Class '" + result.getObjectClass() + "' confidence '" + result.getConfidence() + "'");
        }

        if (!results.isEmpty())
            display.showText(results.get(0).getObjectClass());

        


    }
}
