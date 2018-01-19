package org.nitin.java.eight.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaExpression {

    static String message = "test";

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("nitin");
        names.add("sonam");
        names.add("naveen");
        names.add("moma");
        names.add("pappa");

        message = "trying to change it";
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
        System.out.println(names);

        // Math operation

        MathOperation multiOperation = (a, b) -> {
            return a * b;
        };
        System.out.println(multiOperation.operation(10, 5));
        MathOperation addOperation = (a, b) -> {
            return a + b;
        };
        System.out.println(addOperation.operation(10, 5));

        Display display = d -> System.out.println(message = "test changed" + " -> " + d);

        display.perform("My name is anthony gonselvas...");
    }
}
