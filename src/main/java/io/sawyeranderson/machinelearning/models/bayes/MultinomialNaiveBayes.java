package io.sawyeranderson.machinelearning.models.bayes;

import io.sawyeranderson.machinelearning.util.tokenize.SimpleTokenizer;
import io.sawyeranderson.machinelearning.util.tokenize.Tokenizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultinomialNaiveBayes {
    private final Map<String, Classification> classifications;
    private final Map<Classification, Map<String, Integer>> classTokenFreqs;
    private final Set<String> tokens;

    private Tokenizer tokenizer;

    public MultinomialNaiveBayes() {
        this.classifications = new HashMap<String, Classification>();
        this.classTokenFreqs = new HashMap<Classification, Map<String, Integer>>();
        this.tokens = new HashSet<String>();
        this.tokenizer = new SimpleTokenizer();
    }

    public void train(String content, String classificationName) {
        Classification classification = ensureClassificationExists(classificationName);

        Map<String, Integer> docFreqs = tokenizer.tokenize(content);
        incrementAll(docFreqs, classification);

        classification.incrementNumDocs();
    }

    public Classification classify(String document) {
        Map<Classification, Double> classificationProbabilities = new HashMap<Classification, Double>();

        Map<String, Integer> docFreqs = tokenizer.tokenize(document);
        Map<String, Integer> classFreqs;
        for (Classification classification : classifications.values()) {
            double probability = 1d;
            classFreqs = classTokenFreqs.get(classification);

            for (Map.Entry<String, Integer> entry : docFreqs.entrySet()) {
                Integer freq = classFreqs.get(entry);
                if (freq == null) { freq = 0; }
                freq++; // add-one smoothing

                probability *= freq / ((double) classification.getNumDocs() + tokens.size());
            }
            classificationProbabilities.put(classification, probability);
        }

        return getMax(classificationProbabilities);
    }

    private Classification ensureClassificationExists(String name) {
        Classification classification;

        if (!classifications.containsKey(name)) {
            classification = new Classification(name);
            classifications.put(name, classification);
            classTokenFreqs.put(classification, new HashMap<String, Integer>());
        }
        else {
            classification = classifications.get(name);
        }

        if (!classTokenFreqs.containsKey(classification)) {
            classTokenFreqs.put(classification, new HashMap<String, Integer>());
        }
        return classification;
    }


    private void incrementAll(final Map<String, Integer> docFreqs, final Classification classification) {
        Map<String, Integer> classFreqs = classTokenFreqs.get(classification);
        for (Map.Entry<String, Integer> entry : docFreqs.entrySet()) {
            String key = entry.getKey();

            // increment class frequencies
            if (classFreqs.containsKey(key)) {
                classFreqs.put(key, classFreqs.get(key) + entry.getValue());
            }
            else {
                classFreqs.put(entry.getKey(), entry.getValue());
            }

            tokens.add(key);
        }
    }

    private Classification getMax(Map<Classification, Double> probabilities) {
        Classification maxClass = null;
        Double maxProbability = Double.MIN_VALUE;
        for (Map.Entry<Classification, Double> entry : probabilities.entrySet()) {
            if (entry.getValue() > maxProbability) {
                maxClass = entry.getKey();
                maxProbability = entry.getValue();
            }
        }
        return maxClass;
    }
}
