package io.sawyeranderson.machinelearning.models.bayes;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ClassificationTests {
    private Classification classification;
    private static final double EPSILON = 0.0001d;

    @Before
    public void setUp() throws Exception {
        classification = new Classification("star wars");
    }

    @Test
    public void testUpdateTokensWithDoc() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("star_wars_force_balance.txt").getPath().substring(1);
        String document = readFile(path);
        classification.trainWithDoc(document);
        Map<String, Integer> tokenFreqs = classification.getTokenFrequencies();

        assertEquals(tokenFreqs.get("the").intValue(), 41);
        assertEquals(tokenFreqs.get("jedi").intValue(), 18);
        assertEquals(tokenFreqs.get("yoda").intValue(), 6);
        assertNull(tokenFreqs.get("picard"));
    }

    private static String readFile(String path) throws IOException {
        Charset encoding = Charset.defaultCharset();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}