package io.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo1 {
    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();

        demo4();

    }

    private static void demo4() {
        Stream<String> s = Stream.of("aaaa", "bbb", "ccc", "撒娇地", "撒娇开发");
//        Set<String> c = s.collect(Collectors.toSet());
//        System.out.println(c);

        List<String> a = s.collect(Collectors.toList());
        System.out.println(a);
    }

    private static void demo3() {
        Stream<String> s = Stream.of("aaaa", "bbb", "ccc", "撒娇地", "撒娇开发");
        Stream<String> s1 = Stream.of("===", "----", ";;;", "<<<<", "))))");
        Stream.concat(s, s1).forEach(System.out::println);
    }

    private static void demo2() {
        Stream<String> s = Stream.of("aaaa", "bbb", "ccc", "撒娇地", "撒娇开发");
        Stream<String> ss = s.filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() == 3;
            }
        });
        System.out.println(ss);
        ss.forEach(System.out::println);
    }

    private static void demo1() {
        ArrayList<String> l = new ArrayList<>();
        l.add("AAA");
        l.add("BBB");
        l.add("CCC");
        l.add("DDD");

        Stream<String> s = l.stream();
        s.filter(Predicate.isEqual("AAA"));

        /*
        s.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        */
//        System.out.println(s);
//        s.forEach(v->System.out.println(v));
        System.out.println();
        s.forEach(System.out::println);
    }
}
