package io.sawyeranderson.machinelearning.util.tokenize;

import java.util.*;

public class SimpleTokenizer implements Tokenizer {
    public static final String DEFAULT_DELIMITERS = " \t\n\r\f.,:/\\!?()[]{}=+\'\"";

    private StringTokenizer tokenizer;
    private String delimiters;

    public SimpleTokenizer(){
        delimiters = DEFAULT_DELIMITERS;
    }

    public SimpleTokenizer(final String delimiters) {
        this.delimiters = delimiters;
    }

    public Map<String, Integer> tokenize(String content) {
        Map<String, Integer> tokenFreqs = new HashMap<String, Integer>();
        content = content.toLowerCase(Locale.getDefault());
        tokenizer = new StringTokenizer(content, delimiters);

        String token;
        while (tokenizer.hasMoreElements()) {
            token = tokenizer.nextToken();
            incrementFreq(token, tokenFreqs);
        }
        return tokenFreqs;
    }

    private static void incrementFreq(final String token, final Map<String, Integer> tokenFreqs) {
        if (!tokenFreqs.containsKey(token)) {
            tokenFreqs.put(token, 1);
        }
        else {
            tokenFreqs.put(token, tokenFreqs.get(token) + 1);
        }
    }
}
