package de.patricksteinert.recognizer.util;

import de.patricksteinert.recognizer.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by Patrick Steinert on 26.07.21.
 */
public class FakeRecognitionTest {

    @Test
    public void testRecignition() {
        FakeRecognition fakeRecognition = new FakeRecognition();
        List<Result> result = fakeRecognition.recognize(null);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        Result element = result.get(0);

        assertEquals("unclassified", element.getObjectClass());
        assertEquals(99.9, element.getConfidence());
    }

}
