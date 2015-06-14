package io.sawyeranderson.machinelearning.models.bayes;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class MultinomialNaiveBayesTests {
    private MultinomialNaiveBayes classifier;
    String[] starWarsTrain = {"star_wars_imdb.txt", "star_wars_wikipedia.txt"};
    String[] starTrekTrain = {"star_trek_imdb.txt", "star_trek_wikipedia.txt"};

    String starWarsTest = "star_wars_force_balance.txt";
    String starTrekTest = "star_trek_language.txt";

    @Before
    public void setUp() {
        classifier = new MultinomialNaiveBayes();
    }

    @Test
    public void testClassify() throws Exception {
        // star wars

        for (String file : starWarsTrain) {
            classifier.train(readFile(getPath(file)), "star wars");
        }

        for (String file : starTrekTrain) {
            classifier.train(readFile(getPath(file)), "star trek");
        }

        Classification starWars = classifier.classify(readFile(getPath(starWarsTest)));
        Classification starTrek = classifier.classify(readFile(getPath(starTrekTest)));

        assertEquals(starWars.getName(), "star wars");
        assertEquals(starTrek.getName(), "star trek");
    }

    private String getPath(String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResource(resourceName).getPath().substring(1);
    }

    private static String readFile(String path) throws IOException {
        Charset encoding = Charset.defaultCharset();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}