package de.patricksteinert.recognizer;

import com.srgroup.tfobj.detectors.Classifier;
import com.srgroup.tfobj.detectors.TFObjectDetector;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample recognition uses the TensorFlow ssd_mobilenet_v2_coco net to recognize objects.
 * <p>
 * Created by Patrick Steinert on 19.07.21.
 *
 * @author Patrick Steinert
 * @since 1.0
 */
public class SampleRecognition implements Recognition {

    private final Classifier classifier;

    public SampleRecognition() throws IOException {
        String modelFilePath = "./ssd_mobilenet_v2_coco_2018_03_29/frozen_inference_graph.pb";

        String labelMapFilePath = "./coco_labels.txt";


        classifier = TFObjectDetector.create(modelFilePath, labelMapFilePath);

    }

    public static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
    }

    public static void saveAnnotatedImage(BufferedImage image,
                                          List<Classifier.Recognition> recognitions,
                                          String outputImageFilePath) throws IOException {


        Graphics2D g2D = image.createGraphics();

        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke((image.getWidth() * 5) / 1000));

        // 15. Draw rectangle1.

        for (Classifier.Recognition recognition : recognitions) {

            Rectangle rectangle = new Rectangle(
                    (int) recognition.getLocation().getMinX(),
                    (int) recognition.getLocation().getMinY(),
                    (int) (recognition.getLocation().getWidth() + 0.5),
                    (int) (recognition.getLocation().getHeight() + 0.5));

            g2D.draw(rectangle);
        }

        ImageIO.write(image, "jpeg", new File(outputImageFilePath));
    }

    @Override
    public List<Result> recognize(IplImage image) {

        String outputImageFilePath = Recognizer.TMP_IMAGE_FOLDER + File.separator + (3) + "-aa.jpg";

        List<Result> results = new ArrayList<>();
//            BufferedImage image = ImageIO.read(new File(imageFilePath));
        BufferedImage bImage = IplImageToBufferedImage(image);
        List<Classifier.Recognition> recognitionList = classifier.recognizeImage(bImage);

        for (Classifier.Recognition recognition : recognitionList) {
            System.out.println("Title " + recognition.getTitle() + " Score " + recognition.getConfidence());

            results.add(new Result(recognition.getTitle(), recognition.getConfidence()));
        }

        try {
            saveAnnotatedImage(bImage, recognitionList, outputImageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }


}
