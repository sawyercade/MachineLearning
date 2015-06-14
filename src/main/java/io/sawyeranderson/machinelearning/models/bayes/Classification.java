package io.sawyeranderson.machinelearning.models.bayes;

import io.sawyeranderson.machinelearning.util.tokenize.SimpleTokenizer;
import io.sawyeranderson.machinelearning.util.tokenize.Tokenizer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classification {
    public static final Tokenizer TOKENIZER = new SimpleTokenizer();
    public static int TOTAL_NUM_DOCS = 0;

    private final String name;
    private int numDocs;
    private int numTokens; // sum over tokenFrequencies.getValues(), NOT tokenFrequencies.getSize()
    private final Map<String, Integer> tokenFrequencies;

    public Classification(String name) {
        this.name = name;
        this.numDocs = 0;
        this.numTokens = 0;
        this.tokenFrequencies = new HashMap<>();
    }

    public Map<String, Integer> trainWithDoc(String doc){
        updateTokens(TOKENIZER.tokenize(doc));

        this.numDocs++;
        TOTAL_NUM_DOCS++;

        return getTokenFrequencies();
    }

    /**
     * Calculates P(c) * P(d|c)
     * @param doc
     * @return
     */
    public double getDocProbability(String doc) {
        double prior = ((double)this.numDocs) / TOTAL_NUM_DOCS; // P(c)
        double probability = 1d;
        double prevProbability = probability;

        Map<String, Integer> tokenFreqs = TOKENIZER.tokenize(doc);

        String token;
        Integer frequency;
        // calculate P(d|c) = ?P(w_i | c)
        for (Map.Entry<String, Integer> entry : tokenFreqs.entrySet()) {
            token = entry.getKey();
            frequency = entry.getValue();

            //TODO:SCA: even with log10, we're still getting underflow
            prevProbability = probability;
            probability *= 1d / Math.abs(Math.log10(getTokenProbability(token) * frequency));
        }

        return probability * prior; // P(c) * P(d|c)
    }

    /**
     * Calculates P(w|c)
     * @param token
     * @return
     */
    private double getTokenProbability(String token) {
        Integer frequency = this.tokenFrequencies.get(token);
        frequency = frequency == null ? 1 : frequency + 1;

        // P(w|c) = (count(w, c) + 1) / (numTokens + numUniqueTokens)
        return ((double)frequency) / (numTokens + this.tokenFrequencies.size());
    }

    private void updateTokens(Map<String, Integer> incomingTokenFreqs) {
        String token;
        Integer frequency;
        for (Map.Entry<String, Integer> tokenFreq : incomingTokenFreqs.entrySet()) {
            token = tokenFreq.getKey();
            frequency = tokenFreq.getValue();
            if (this.tokenFrequencies.containsKey(token)) {
                this.tokenFrequencies.put(token, this.tokenFrequencies.get(token) + frequency);
            }
            else {
                this.tokenFrequencies.put(token, frequency);
            }
            this.numTokens += frequency;
        }
    }

    //region Accessors
    public Map<String, Integer> getTokenFrequencies() {
        return tokenFrequencies;
    }

    public String getName() {
        return name;
    }

    public int getNumDocs() {
        return numDocs;
    }
    //endregion
}
