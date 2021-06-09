package lang.java8.functional;

import java.util.Comparator;
import java.util.TreeSet;

public class Lambda {
    public static void main(String[] args) {
//        test1();
        test2();

    }

    private static void test2() {
        final Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
        final TreeSet<String> strings = new TreeSet<>(comparator);
        strings.add("a");
        strings.add("b");
        strings.add("c");
        System.out.println(strings);

        Comparator<String> comparator2 = ((o1, o2) -> o1.length() - o2.length());
        final TreeSet<String> strings1 = new TreeSet<>(comparator2);
        strings1.add("a");
        strings1.add("z");
        System.out.println(strings1);

        Comparator<String> comparator3 = Comparator.comparingInt(String::length);
    }

    private static void test1() {
        Runnable runnable = () ->System.out.println("a");
        new Thread(runnable).start();

        new Thread(() ->System.out.println("b")).start();

        new Thread(() -> System.out.println("c")).start();
    }
}
