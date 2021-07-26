package de.patricksteinert.recognizer;

/**
 * Display class contains all features to print text on the two line display.
 * <p>
 * Created by Patrick Steinert on 21.06.21.
 *
 * @author Patrick Steinert
 * @since 1.0
 */
public class Display {

    /**
     * Present a single line.
     *
     * @param text text as string
     */
    public void showText(String text) {
        // First Step: just print the text on the command line.
        System.out.println(text);
    }

    /**
     * Present a multi line text.
     * @param line1 text to show on the first line
     * @param line2 text to show on the second line
     */
    public void showMultiline(String line1, String line2) {
        // First Step: just print the text on the command line.
        System.out.println(line1);
        System.out.println(line2);
    }
}
