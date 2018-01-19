package com.jaba.eight.examples;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.jaba.eight.examples.Dream.FREQUENCY;

public class Revision2 {
    // Supplier example
    // match example
    // Bifunction, BiPredicate,
    // Stream, filter, sorting , groupby, reduce
    // Map to list
    // List to Map
    // Boolean, Float, Double
    // Range
    // String joiners
    // Function reference
    // Constructor reference
    // Lambda expression
    // Annotation support
    // https://www.javacodegeeks.com/2014/05/java-8-features-tutorial.html#extended_annotations_support
    // Get parameters method in reflection
    // Optional class
    // Date time instancez22
    // Parallel Arrays
    // Findany, findAll, FindFirst
    // 9410-> Ⓜ
    // 1229-> Ӎ
    // 1230-> ӎ

    // Aalay@9410
    // A@l@y#9410

    @Test
    public void testStreamFun() {
        List<Dream> dreamList = Dream.getDreamList();
        Comparator<Dream> comp = Comparator.comparing(Dream::getCost);
        BinaryOperator<Dream> binary = BinaryOperator.maxBy(comp);
        Map<FREQUENCY, Optional<Dream>> collect = dreamList.stream().filter(d -> d.getFrequency() != null)
                .collect(Collectors.groupingBy(Dream::getFrequency, Collectors.reducing(binary)));
        Assert.assertEquals(4000000, collect.get(FREQUENCY.YEARLY).get().getCost().intValue());
    }

    @Test
    public void supplierTest() {
        Supplier<Dream> dreamSupplier = () -> new Dream("Banglow", 30_00_000);
        System.out.println(dreamSupplier.get());
    }

    @Test
    public void matchTest() {
        List<Dream> dreamList = Dream.getDreamList();
        Predicate<? super Dream> anyMatchPredicate = (d) -> d.title.equals("MORE_FIN_SEC");
        boolean anyMatch = dreamList.stream().anyMatch(anyMatchPredicate);
        Assert.assertTrue(anyMatch);

        Predicate<? super Dream> allMatchPredicate = (d) -> d.title.equals("MORE_FIN_SEC");
        Assert.assertFalse(dreamList.stream().allMatch(allMatchPredicate));

        Predicate<? super Dream> noneMatchPredicate = (d) -> d.title.equals("NONE_MATCH");

        Assert.assertTrue(dreamList.stream().noneMatch(noneMatchPredicate));
    }

    @Test
    public void biTest() {
        BiFunction<String, String, String> biFunction = (s1, s2) -> s1.concat(" ").concat(s2);
        BiConsumer<Boolean, String> biConsumer = (a, b) -> System.out.println(a + " and " + b);
        BiPredicate<Integer, String> biPredicate = (t, u) -> Integer.parseInt(u) == t;
        String apply = biFunction.apply("Nitin", "Upadhyay");
        boolean test = biPredicate.test(5, "5");
        biConsumer.accept(test, apply);
        Assert.assertTrue(test);
        Assert.assertEquals("Nitin Upadhyay", apply);
    }

}
