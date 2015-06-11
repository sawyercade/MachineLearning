package io.sawyeranderson.machinelearning.util.tokenize;

import java.util.Map;

public interface Tokenizer {
    Map<String, Integer> tokenize(String content);
}
