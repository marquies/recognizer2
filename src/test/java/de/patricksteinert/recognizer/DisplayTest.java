package de.patricksteinert.recognizer;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for Display. Just Testing print to System.out because of lack of real display.
 *
 * Created by Patrick Steinert on 26.07.21.
 */
public class DisplayTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testMultiline() {
        Display display = new Display();
        display.showMultiline("Hello", "World!");

        assertEquals("Hello\nWorld!", outContent.toString().trim());
    }

}
