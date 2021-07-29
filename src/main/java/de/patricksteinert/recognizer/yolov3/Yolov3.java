package de.patricksteinert.recognizer.yolov3;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.tensorflow.Graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Patrick Steinert on 23.07.21.
 */
public class Yolov3 {
    private ComputationGraph model;

    public Yolov3() {

        try {
            model = KerasModelImport.importKerasModelAndWeights("yolo_v3_fruits360_2021_07_23_4/yolo_model_export.h5", false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        } catch (InvalidKerasConfigurationException e) {
            e.printStackTrace();
        }

        try (Graph graph = new Graph()) {
            //graph.importGraphDef(Files.readAllBytes(Paths.get("./yolo_v3_fruits360_2021_07_23_4/saved_model/saved_model.pb")));
//            SavedModelBundle saved_model = SavedModelBundle.load("./yolo_v3_fruits360_2021_07_23_4/saved_model/", "serve");

        }

    }

    public byte[] getGraph() {
        return null;
    }

    public void inference(String imagePath) {
        try {
            predict(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public INDArray predict(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            file = new File(filepath);
        }

        BufferedImage img = ImageIO.read(file);
        double data[] = new double[28 * 28];
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                Color color = new Color(img.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                double greyScale = (r + g + b) / 3;
                greyScale /= 255.;
                data[i * 28 + j] = greyScale;
            }
        }

        INDArray arr = Nd4j.create(data).reshape(-1, 28 * 28);
        //Map<String,INDArray> placeholder = new HashMap<>();
        //placeholder.put("input",arr);
        INDArray output = model.outputSingle(arr);
        System.out.println(Arrays.toString(output.reshape(10).toDoubleVector()));
        return output;

    }
}
