package de.patricksteinert.recognizer;

import de.patricksteinert.recognizer.Recognition;
import de.patricksteinert.recognizer.Result;
import de.patricksteinert.recognizer.SampleRecognition;
import de.swa.gmaf.plugin.GMAF_Plugin;
import de.swa.mmfg.MMFG;
import de.swa.mmfg.Node;
import de.swa.mmfg.TechnicalAttribute;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

/**
 * Adapter to run the object recognition procedures inside the GMAF.
 * <p>
 * Created by Patrick Steinert on 20.07.21.
 *
 * @author Patrick Steinert
 * @since 1.0
 */
public class GMAF_PluginAdapter implements GMAF_Plugin {

    /**
     * Recognition method
     */
    private Recognition recognition;

    /**
     * GMAF Variable for detected nodes.
     */
    private Vector<Node> detectedNodes = new Vector<Node>();

    /**
     * Get the detected node of the instance. The nodes are populated during the process call.
     *
     * @return the detected nodes of the instance.
     */
    public Vector<Node> getDetectedNodes() {
        return detectedNodes;
    }

    /**
     * Process a file.
     * <ol>
     * <li>Load file</li>
     * <li>Do recognition</li>
     * <li>Add to MMFG and detectedNodes</li>
     *  </ol>
     *
     * @param url   unused
     * @param file  file to process
     * @param bytes unused
     * @param mmfg  existing MMFG
     */
    @Override
    public void process(URL url, File file, byte[] bytes, MMFG mmfg) {
        // Read File
        this.recognition = new SampleRecognition();

        System.out.println(file.getAbsolutePath());
        Mat image = imread(file.getAbsolutePath());
        if (image.isNull()) {
            System.out.println("Image is null");
        }

        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        Frame frame = matConverter.convert(image);

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        IplImage img = converter.convert(frame);

        List<Result> foo = recognition.recognize(img);

        Node currentNode = mmfg.getCurrentNode();

        for (Result result : foo) {
            Node n = new Node(result.getObjectClass(), mmfg);
            n.addTechnicalAttribute(new TechnicalAttribute(0, 0, 100, 100, 1.0f, 0.0f));

            detectedNodes.add(n);

            currentNode.addChildNode(n);
        }

    }

    /**
     * Returns true if the plugin supports recursive data.
     *
     * @return False. Always. Ever.
     */
    @Override
    public boolean providesRecoursiveData() {
        return false;
    }

    /**
     * Returns true if the plugin is a general plugin.
     *
     * @return False. Always. Ever.
     */
    @Override
    public boolean isGeneralPlugin() {
        return false;
    }

    /**
     * Returns true, if the extension can be processed by the plugin.
     *
     * @param extension extension string of a file.
     * @return true is the plugin can process files with the extension.
     */
    @Override
    public boolean canProcess(String extension) {
        if (extension.toLowerCase().endsWith("png"))
            return true;
        if (extension.toLowerCase().endsWith("jpg"))
            return true;
        if (extension.toLowerCase().endsWith("jpeg"))
            return true;
        return false;
    }
}
