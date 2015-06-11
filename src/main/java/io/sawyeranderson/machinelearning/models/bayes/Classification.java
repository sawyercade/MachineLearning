package io.sawyeranderson.machinelearning.models.bayes;

public class Classification {
    private String name;
    private int numDocs;

    public Classification(String name) {
        this.name = name;
        numDocs = 0;
    }

    public int incrementNumDocs() {
        return this.numDocs++;
    }

    public String getName() {
        return name;
    }

    public int getNumDocs() {
        return numDocs;
    }
}
