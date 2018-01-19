package com.jaba.eight.examples;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.jaba.eight.examples.Dream.FREQUENCY;
import com.jaba.eight.examples.Dream.Format;
import com.jaba.eight.examples.Dream.INSTRUMENT;

public class SupplierExampleTest {
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

    // 9410-> Ⓜ
    // 1229-> Ӎ
    // 1230-> ӎ

    // Aalay@9410

    @Test
    public void testChar() {
        java.util.stream.IntStream.range(1000, 9999).map(i -> (char) i)
                .forEach(i -> System.out.println(i + "-> " + (char) i));

    }

    @Test
    public void testParallelArraySecond() {
        String[] arr = new String[Dream.getKeyList().size()];
        IntFunction generator = index -> Dream.getKeyList().get(index);
        Arrays.parallelSetAll(arr, generator);

        List<String> sorted = Arrays.stream(arr).limit(3).sorted().collect(Collectors.toList());
        System.out.println(sorted);
    }

    @Test
    public void testDateTime_II() {
        Instant instant = (new Date()).toInstant();
        Instant instant2 = Instant.now(Clock.systemUTC());

        Duration duration = Duration.between(instant, instant2);
        System.out.println(duration.getNano());

        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.DECEMBER, 13, 5, 56);
        duration = Duration.between(instant2, localDateTime.toInstant(ZoneOffset.UTC));
        System.out.println(duration.getSeconds());
    }

    @Test
    public void testOptional_II() {
        Optional<Dream> dream = Dream.getDreamList().stream().filter(d -> d.cost < 10000).findFirst();
        if (dream.isPresent()) {
            System.out.println("yes we have cheaper dream , d = " + dream.get());
        }

        Dream orElse = dream.orElse(new Dream("Created newly", 4000));
        System.out.println("yes we have new cheaper dream , d = " + orElse);

        Supplier<Dream> supplier = () -> new Dream("Created by supplier", 8000);
        Dream orElseGet = dream.orElseGet(supplier);
        System.out.println(orElseGet);

    }

    @Test
    public void test8thDec2017() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method method = Dream.class.getMethod("setSip", Integer.class);
        for (Parameter t : method.getParameters()) {
            System.out.println(t);
        }
        Dream dream = new Dream("temp", 0);
        method.invoke(dream, 5000);

        Assert.assertEquals(5000, dream.getSip().intValue());
    }

    @Test
    public void test7thDec2017() {
        Password password = (s) -> {
            if (s.equals(Password.getPhrase())) {
                return "mypassword";
            }
            throw new RuntimeException("invalid passphrase");
        };
        Assert.assertEquals("mypassword", password.getPassword("f@m"));
        Assert.assertEquals("defaultPassword", password.getDefaultPassword());
    }

    @Test
    public void test6thDec2017() {
        Password password = (s) -> {
            if (s.equals(Password.getPhrase())) {
                return "mypassword";
            }
            throw new RuntimeException("invalid passphrase");
        };
        Assert.assertEquals("mypassword", password.getPassword("f@m"));
        Assert.assertEquals("defaultPassword", password.getDefaultPassword());
    }

    @FunctionalInterface
    interface Password {
        String getPassword(String passwordPhrace);

        static String getPhrase() {
            return "f@m";
        }

        default String getDefaultPassword() {
            return "defaultPassword";
        }
    }

    @Test
    public void testConstructorReference() {
        DreamMaker dream = Dream::new;
        Dream instance = dream.getInstance("test", 40000, 2000, FREQUENCY.MONTHLY);
        Assert.assertEquals(40000, instance.getCost().intValue());
    }

    @Test
    public void testParallelArraySort() {
        long[] longArray = new long[1000];
        Arrays.parallelSetAll(longArray, index -> ThreadLocalRandom.current().nextInt(10000));

        Arrays.stream(longArray).limit(10).forEach(System.out::println);

        Arrays.parallelSort(longArray);
        System.out.println("after sort");
        Arrays.stream(longArray).limit(10).forEach(System.out::println);
    }

    private static interface ExpensiveDream {
        default Optional<Dream> getExpensive() {
            Optional<Dream> collect = Dream.getDreamList().stream().filter(d -> d.getSip() != null)
                    .collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dream::getSip))));
            return collect;
        }
    }

    public class Arithmetic {

        int add(int a, int b) {
            return a + b;
        };

        int add(int a, int b, int c) {
            return a + b + c;
        };
    }

    @FunctionalInterface
    public interface TriFunction<R, X, Y, Z> {
        R add(X a, Y b, Z c);
    }

    @Test
    public void test4Dec2017() {
        // ExpensiveDream dream = ExpensiveDream::getExpensive;
        BiFunction<Integer, Integer, Integer> bi = new Arithmetic()::add;
        System.out.println(bi.apply(5, 6));

        TriFunction<Integer, Integer, Integer, Integer> tri = new Arithmetic()::add;
        System.out.println(tri.add(4, 6, 7));
    }

    @Test
    public void testReducer() {
        List<Employee> employees = Employee.createListOfEmployee();
        Comparator<Employee> comp = Comparator.comparing(Employee::getSalary);
        Map<String, Optional<Employee>> groupBy = employees.stream().sorted(comp)
                .collect(Collectors.groupingBy(Employee::getDept, Collectors.reducing(BinaryOperator.maxBy(comp))));
        Assert.assertEquals(9000, groupBy.get("A").get().salary);
        Assert.assertEquals(8000, groupBy.get("B").get().salary);
    }

    @Test
    public void IntStream() {
        java.util.stream.IntStream.range(1, 10).forEach(System.out::println);
    }

    @Test
    public void TransformStream() {
        Stream.of(1.0, 2.0, 3.0).map(Double::intValue).map(e -> "a" + e).forEach(System.out::println);
    }

    @Test
    public void testMatchExample() {
        List<Employee> employees = Employee.createListOfEmployee();
        Assert.assertTrue(employees.stream().allMatch(e -> e.name.endsWith("sh")));
        Assert.assertTrue(employees.stream().anyMatch(e -> e.name.startsWith("Na")));
        Assert.assertTrue(employees.stream().noneMatch(e -> e.name.startsWith("Po")));
    }

    // http://www.mkyong.com/java/java-check-if-array-contains-a-certain-value/
    @Test
    public void bifunction() {
        BiConsumer<String, Integer> template = (s, i) -> System.out.println("My name is " + s + " and my age is " + i);
        template.accept("Nitin", 33);
        BiFunction<String, String, String> biFunction = (s1, s2) -> s1.concat(s2);
        Assert.assertEquals("Nitin Upadhyay", biFunction.apply("Nitin", " Upadhyay"));
        BiPredicate<Integer, Integer> isGreaterThan = (i, j) -> i > j;

        Assert.assertTrue(isGreaterThan.test(8, 7));
        Assert.assertFalse(isGreaterThan.test(7, 8));
    }

    // map to list example
    @Test
    public void testMapToList() {
        List<Employee> employees = Employee.createListOfEmployee();
        employees = new ArrayList<Employee>(employees);
        employees.add(new Employee("5", "Ilesh", 500000, "I"));
        Map<String, Employee> map = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getEmployee, (o, n) -> o, LinkedHashMap::new));
        Assert.assertEquals("Naresh", map.get("5").getName());
    }
    // Default interface

    @Test
    public void testDefaultInterfaceMethod() {
        InterfaceWithDefault id = new InterfaceWithDefault() {
        };
        Assert.assertEquals(Math.sqrt(9), id.sqrt(9), 0.00000);
    }
    // https://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&source=web&cd=9&ved=0ahUKEwip78P4zajXAhUKuI8KHRvJAHsQFghYMAg&url=https%3A%2F%2Fbeginnersbook.com%2F2017%2F10%2Fjava-8-features-with-examples%2F&usg=AOvVaw2iRe9ffobPeNyXId_5n_r0

    @Test
    public void testMapToListRev() {
        List<String> dreamMapList = Dream.getKeyList();
        Assert.assertEquals(6, dreamMapList.size());
        Assert.assertEquals("PPF", dreamMapList.get(0));
    }

    @Test
    public void testStringJoiner() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        sj.setEmptyValue("This is a default value");

        Assert.assertEquals("This is a default value", sj.toString());

        Dream.getKeyList().forEach(e -> sj.add(e));
        Assert.assertEquals("[PPF, SBI, LIC8K, HI, LIC48K, PF]", sj.toString());

        StringJoiner sj2 = new StringJoiner(", ", "[", "]");
        Dream.getValueList().forEach(e -> sj2.add(e.getTitle()));
        sj.merge(sj2);

        Assert.assertEquals("[PPF, SBI, LIC8K, HI, LIC48K, PF, MtPt, Golden Bangle, Comp, Health, Retiaral, Pension]",
                sj.toString());
    }

    @Test
    public void testIntegerFloatBooleanStreams() {
        // A = P*(1+R/N)^Nt
        java.util.stream.IntStream.rangeClosed(1, 10).forEach(i -> System.out.println(i));

    }

    // http://www.baeldung.com/java-8-streams

    // Parallel sort
    @Test
    public void testParallelSort() {
        List<Dream> dreamMapList = Dream.getDreamList();
        List<Dream> sortedDream = dreamMapList.parallelStream().sorted(Comparator.comparing(Dream::getCost)).skip(3)
                .limit(2).collect(Collectors.toList());
        sortedDream.forEach(System.out::println);
        Assert.assertEquals("Golden Bangle", sortedDream.get(0).getTitle());
    }

    @Test
    public void testDistinct() {
        List<Dream> dreamMapList = Dream.getDreamList();
        List<Dream> sortedDream = dreamMapList.parallelStream().distinct().collect(Collectors.toList());
        int yearlyCost = dreamMapList.stream().distinct().filter(e -> e.frequency == FREQUENCY.YEARLY)
                .mapToInt(e -> e.sip).sum();
        sortedDream.forEach(System.out::println);
        Assert.assertEquals("MtPt", sortedDream.get(0).getTitle());
        Assert.assertEquals(240000, yearlyCost);
    }

    @Test
    public void testGroupByFunction() {
        Map<FREQUENCY, List<Dream>> newMap = Dream.getMap().keySet().stream()
                .filter(e -> Dream.getMap().get(e).getFrequency() != null).map(e -> Dream.getMap().get(e))
                .collect(Collectors.groupingBy(Dream::getFrequency));
        newMap.entrySet().stream().forEach(System.out::println);
        Comparator<Dream> sipComparator = Comparator.comparing(Dream::getSip);
        Map<FREQUENCY, Optional<Dream>> newMapWithMax = Dream.getMap().keySet().stream()
                .filter(e -> Dream.getMap().get(e).getFrequency() != null).map(e -> Dream.getMap().get(e))
                .collect(Collectors.groupingBy(Dream::getFrequency,
                        Collectors.reducing(BinaryOperator.maxBy(sipComparator))));
        newMapWithMax.entrySet().stream().forEach(System.out::println);
        Assert.assertEquals(11000, newMapWithMax.get(FREQUENCY.MONTHLY).get().sip.intValue());
        Assert.assertEquals(48000, newMapWithMax.get(FREQUENCY.YEARLY).get().sip.intValue());
    }

    // http://javapapers.com/core-java/java-lambda-expression-examples/
    @Test
    public void testLambdaExpression() {
        Format formattedString = (String s) -> "Special formation" + s;
        List<String> listString = Dream.getDreamList().stream().map(e -> formattedString.format(e.getTitle()))
                .collect(Collectors.toList());
        listString.stream().forEach(System.out::println);

        List<String> defaultString = Dream.getDreamList().stream().map(e -> formattedString.formatSimple(e.getTitle()))
                .collect(Collectors.toList());
        defaultString.stream().forEach(System.out::println);

    }

    @Test
    public void testLambdaInitilization() throws Exception {
        Callable[] animals = new Callable[] { () -> "Lion", () -> "Dog" };
        // Arrays.asList(animals).forEach(Callable::call);
        System.out.println(animals[0].call());

    }

    @Test
    public void testLambdaRevision() {
        Interest interest = (double p, double r, int t) -> {
            double sum = java.util.stream.IntStream.range(1, t).mapToDouble(n -> {
                double d = p * Math.pow(1 + r / 100, n);
                System.out.println(n + " -> Cal: " + BigDecimal.valueOf(d));
                return d;
            }).sum();
            return sum;
        };
        System.out.println(interest.calculate(12000, 8.33, 20));
        System.out.println(BigDecimal.valueOf(interest.calculate(100000, 15, 20)));
        System.out.println(BigDecimal.valueOf(interest.calculate(100000, 8, 29)));
    }

    @Test
    public void testArraySorting() {
        Dream[] array = Dream.getDreamList().toArray(new Dream[0]);
        Arrays.sort(array, Dream::compareTitle);
        for (Dream d : array) {
            System.out.println("Print::" + d);
        }
    }

    @Test
    public void testBiOperations() {
        BiFunction<Integer, Integer, Integer> biFunction = (i, j) -> i * j;
        Assert.assertEquals(9, biFunction.apply(3, 3).intValue());
        BiConsumer<Integer, Integer> biConsumer = (i, j) -> {
            i++;
            j++;
        };
        Integer i = 5, j = 6;
        biConsumer.accept(i, j);
        Assert.assertEquals(11, i + j);

        BiPredicate<Integer, Integer> biPredicate = IntegerOperation::isGreaterThan;
        Assert.assertTrue(biPredicate.test(9, 6));
        Assert.assertFalse(biPredicate.test(6, 9));
    }

    @Test
    public void testFunctionReference() {
        Function<String, Double> stringToDouble = Double::parseDouble;
        Assert.assertEquals(2.44D, stringToDouble.apply("2.44").doubleValue(), 0.0001);
        Function<String, Double> stringToDoubleLambda = (d) -> Double.parseDouble(d);
        Assert.assertEquals(2.44D, stringToDoubleLambda.apply("2.44").doubleValue(), 0.0001);
        BiPredicate<List<Integer>, Integer> isPartOf = List::contains;
    }

    @Test
    public void testContructorReference() {
        DreamMaker dm = Dream::new;
        Dream dream = dm.getInstance("test", 40000, 2000, FREQUENCY.MONTHLY);
        Assert.assertEquals(40000, dream.getCost().intValue());
        // Generic example
        GenericMaker<Dream> dm2 = Dream::new;

        Dream dream2 = dm2.getInstance("test", 50000, 2000, FREQUENCY.MONTHLY);
        Assert.assertEquals(50000, dream2.getCost().intValue());
    }

    @Test
    public void test2711() {
        List<Dream> dreams = Dream.getDreamList();
        Map<FREQUENCY, Optional<Dream>> groupBy = dreams.stream().filter(e -> e.frequency != null)
                .collect(Collectors.groupingBy(Dream::getFrequency,
                        Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dream::getSip)))));
    }

    @Test
    public void test2811() {
        Map<INSTRUMENT, Dream> map = Dream.getMap();
        List<Dream> dream = map.keySet().stream().filter(e -> map.get(e) != null && map.get(e).getFrequency() != null)
                .map(e -> map.get(e)).collect(Collectors.toList());
        dream.forEach(System.out::println);
    }

    @Test
    @SuppressWarnings("unused")
    public void testAnnotation() {
        final Holder<String> holder = new @NonEmpty Holder<>();
        @NonEmpty
        List<@NonEmpty String> list = new ArrayList<>();
    }

    @Test
    public void testInstant() {
        Long time = 1511771793L;
        Instant instant = Instant.ofEpochMilli(1511845370000L);

        System.out.println(instant);

        instant = instant.plusMillis(Instant.now().toEpochMilli());
        System.out.println(instant);
        System.out.println(instant.toEpochMilli());
    }

    public static class Holder<@NonEmpty T> extends @NonEmpty Object {
        public void method() throws @NonEmpty Exception {
            System.out.println("Testing");
        }
    }

    @Test
    public void parameterNametest() throws NoSuchMethodException, SecurityException {
        Method method = Dream.class.getMethod("setCost", Integer.class);
        for (Parameter test : method.getParameters()) {
            System.out.println(test);
        }
    }

    @Test
    public void listToMapTest2911() {
        Map<String, Integer> map = Dream.getDreamList().stream()
                .collect(Collectors.toMap(Dream::getTitle, Dream::getCost, (o, n) -> n, LinkedHashMap::new));
        System.out.println(map);
    }

    @Test
    public void testOptional() {
        Optional<Dream> dream = Optional.ofNullable(new Dream("actual", 70000));
        System.out.println(dream.isPresent());
        Dream orElse = dream.orElse(new Dream("Temp", 0));
        System.out.println(orElse);
        Supplier<? extends Dream> what = () -> new Dream("temp3", 3);
        Dream orElseGet = dream.orElseGet(what);
        System.out.println(orElseGet);
    }

    @Test
    public void test3012() {
        System.out.println(DoubleStream.of(2.0, 3.5, 4.6).average());
        System.out.println(DoubleStream.of(2.0, 3.5, 4.6).count());
        Map<Long, Long> map = new LinkedHashMap<>();
        LongStream.range(12, 32).forEach(i -> map.put(i, i * i));

        System.out.println(map);
    }

    @Test
    public void testDateTime() {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        LocalDateTime l1 = LocalDateTime.of(1984, Month.JUNE, 12, 7, 30, 0);
        LocalDateTime l2 = LocalDateTime.of(2017, Month.DECEMBER, 1, 7, 30, 0);

        Duration d = Duration.between(l1, l2);
        System.out.println(d.toDays());
        System.out.println(d.toHours());
    }

    @Test
    public void test01Dec() {
        IntFunction<String> test = i -> i + "%";
        List<String> list = Dream.getDreamList().stream().filter(d -> d.getSip() != null).mapToInt(Dream::getSip)
                .mapToObj(test).collect(Collectors.toList());
        System.out.println(list);

        StringJoiner sj = new StringJoiner(",", "{", "}");
        sj.setEmptyValue("UNKNOWN");
        System.out.println(sj);
        list.stream().forEach(sj::add);
        sj.add(null);
        System.out.println(sj);
    }

    public void testLab() {
        System.out.println(55 + 10 + 15 + 20);
    }

}
