package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Exceptions.InvalidException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class VariableValidator {
    public void validate(String variable, String... variables) {
        List<String> list = new ArrayList<>(Arrays.asList(variables));
        list.add(variable);
        Pattern pattern = Pattern.compile("[\\|/|.|\\s|\b|\n|\r|\\\\U00A7]");
        for (String v : list) {
            if (pattern.matcher(v).find()) {
                throw new InvalidException("Invalid input: " + v);
            }
        }
    }
}
