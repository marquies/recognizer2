package de.patricksteinert.recognizer;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Patrick Steinert on 20.07.21.
 */

public class LabelsTest {

    @Test
    public void testCocoLabelsFileForCorrectIndexing() throws Exception {
        Vector<String> labels = new Vector<>();
        InputStream labelsInput = new FileInputStream(new File("coco_labels.txt"));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(labelsInput))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Adding label " + line);
                labels.add(line);
            }
        }
        assertEquals("person", labels.get(1));
    }

}
