package io.sawyeranderson.machinelearning.models.bayes;

import java.util.HashMap;
import java.util.Map;

public class MultinomialNaiveBayes {
    private final Map<String, Classification> classifications;

    public MultinomialNaiveBayes() {
        this.classifications = new HashMap<>();
    }

    public void train(String content, String classificationName) {
        Classification classification = ensureClassificationExists(classificationName);
        classification.trainWithDoc(content);
    }

    public Classification classify(String document) {
        Classification best = null;
        double bestProbability = Double.MIN_VALUE;
        double currentProbability;

        for (Classification classification : classifications.values()) {
            currentProbability = classification.getDocProbability(document);
            if (currentProbability > bestProbability) {
                best = classification;
                bestProbability = currentProbability;
            }
        }

        if (best == null) {
            throw new RuntimeException("Unable to classify, no classification found.");
        }
        return best;
    }

    private Classification ensureClassificationExists(String name) {
        Classification classification;

        if (!classifications.containsKey(name)) {
            classification = new Classification(name);
            classifications.put(name, classification);
        }
        else {
            classification = classifications.get(name);
        }

        return classification;
    }
}
