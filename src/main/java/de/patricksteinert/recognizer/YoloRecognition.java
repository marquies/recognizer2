package de.patricksteinert.recognizer;

import org.bytedeco.opencv.opencv_core.IplImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

/**
 * Recognition for the YoloV3 net from a keras export.
 * <p>
 * Created by Patrick Steinert on 23.07.21.
 *
 * @author Patrick Steinert.
 * @since 1.0
 */
public class YoloRecognition implements Recognition {

    /**
     * Process a recognition for the given image.
     *
     * @param image image to process.
     * @return recognized objects.
     */
    @Override
    public List<Result> recognize(IplImage image) {
//        Yolov3 y = new Yolov3();
//        y.inference("./images/2-aa.jpg");
        Result r = executePythonInference();
        List<Result> results = new Vector();
        results.add(r);

        return results;
    }

    public Result executePythonInference() {
        String s = null;
        Result r = null;

        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:

            Process p = Runtime.getRuntime().exec("python3 ./src/main/resources/inference.py");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            int i = 1;
            String result = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println("*" + s + "*");
                result += s;
            }
            String[] resultParts = result.split(",");
            String label = resultParts[0];
            Double score = Double.valueOf(resultParts[1]);
            r = new Result(label, score);


            String[] boxDim = resultParts[2].stripLeading().stripTrailing().split(" ");


            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
        return r;
    }
}
