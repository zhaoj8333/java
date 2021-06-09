package lang.java8.functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * import static
 * 导入某各类的静态方法
 */
import static java.util.stream.Collectors.joining;

@SuppressWarnings("all")
public class Programmers {

    private static String[] players = {"Rafael Nadal", "Novak Djokovic",
            "Stanislas Wawrinka", "David Ferrer",
            "Roger Federer", "Andy Murray",
            "Tomas Berdych", "Juan Martin Del Potro",
            "Richard Gasquet", "John Isner"};

    private static List<Person> javaProgrammers;
    private static List<Person> phpProgrammers;

    public static void main(String[] args) {
//        example();
//        anonymouseClass();
//        sort();
//        whatDo();
//        sumStat();
        test();
    }

    private static void test() {
    }

    private static void init() {
        javaProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
            }
        };
        phpProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
                add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
                add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
                add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
                add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
                add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
                add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
                add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
                add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
                add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
            }
        };
    }

    private static void sumStat() {
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final IntSummaryStatistics iss = integers.stream().mapToInt((x)->x).summaryStatistics();
        System.out.println(iss);
    }

    private static void print() {
        System.out.println("遍历: ");
        javaProgrammers.forEach((p) -> System.out.printf("%s %s %s;\n", p.getFirstName(), p.getLastName(), p.getSalary()));
        System.out.println();
//        phpProgrammers.forEach((p) -> System.out.printf("%s %s %s;\n", p.getFirstName(), p.getLastName(), p.getSalary()));
    }

    private static void whatDo() {
        init();
//        print();
//        System.out.println("加薪");
        Consumer<Person> raiseSalary = s -> s.setSalary(s.getSalary() / 100 * 50 + s.getSalary());
        javaProgrammers.forEach(raiseSalary);
//        print();
        System.out.println("月薪超过2900的程序员: ");
//        javaProgrammers.io.stream().filter((p)
//                -> (p.getSalary() > 2900)).forEach((p)
//                -> System.out.printf("%s %s %s\n", p.getFirstName(), p.getLastName(), p.getSalary()));
        Predicate<Person> ageFilter = (p) -> (p.getAge() > 25);
        Predicate<Person> salaryFilter = (p) -> (p.getSalary() > 2800);
        Predicate<Person> genderFilterr = (p) -> ("female".equals(p.getGender()));
//        System.out.println(ageFilter);
        System.out.println();
        System.out.println("年龄大于24且月薪2800以上的程序员");
        javaProgrammers.stream()
                .filter(ageFilter)
                .filter(salaryFilter)
                .sorted((p1, p2) -> p1.getGender().compareTo(p2.getGender()))
                .sorted((p1, p2) -> new Integer(p1.getAge()).compareTo(new Integer(p2.getAge())))
                .forEach((p) -> System.out.printf(
                        "%s %s %s %s %s\n",
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAge(),
                        p.getGender(),
                        p.getSalary()
                ));
        System.out.println();
        final Person person = phpProgrammers.stream()
                .max((p1, p2)->(p1.getSalary() - p2.getSalary()))
                .get();
        System.out.println(person);
        System.out.println();
        final String collect = javaProgrammers.stream()
                .limit(5)
                .map((p)->p.getFirstName())
                .collect(joining(" ; "));
        System.out.println(collect);
        System.out.println();
        final Set<String> collect1 = javaProgrammers.stream()
                .limit(5)
                .map(Person::getLastName)
                .collect(Collectors.toSet());
        System.out.println(collect1);
        final int sum = javaProgrammers.parallelStream()
                .mapToInt(Person::getSalary)
                .sum();
        System.out.println(sum);

        System.out.println();



    }

    private static void sort() {

        /*
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(Arrays.toString(players));
         */
        /*
        Comparator<String> sortBy = (String s1, String s2) -> s1.compareTo(s2);
        Arrays.sort(players, sortBy);
        System.out.println(Arrays.toString(players));
         */
        /*
        Arrays.sort(players, (String s1, String s2) -> s1.compareTo(s2));
        System.out.println(Arrays.toString(players));
         */

//        Arrays.sort(players, Comparator.naturalOrder());
//        System.out.println(Arrays.toString(players));

        /*
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1.substring(o1.indexOf(" ")).compareTo(o2.substring(o2.indexOf(" "))));
            }
        });
        System.out.println(Arrays.toString(players));
         */
        /*
        Comparator<String> sortBySurname = (String s1, String s2) -> (
                s1.substring(s1.indexOf(" ")).compareTo(s2.substring(s2.indexOf(" ")))
        );
        Arrays.sort(players, sortBySurname);
        System.out.println(Arrays.toString(players));
         */
        /*
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        System.out.println(Arrays.toString(players));
         */
        /*
        Arrays.sort(players, (String s1, String s2) -> (s1.length() - s2.length()));
        System.out.println(Arrays.toString(players));
         */
        /*
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1.charAt(o1.length() - 1)) - o2.charAt(o2.length() - 1);
            }
        });
        System.out.println(Arrays.toString(players));
         */
        Arrays.sort(players, (String s1, String s2) -> (s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1)));
        System.out.println(Arrays.toString(players));
    }

    private static void anonymouseClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello, world");
            }
        }).start();
        new Thread(() -> System.out.println("hello, world 1")).start();
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello, world 2");
            }
        };
        final Runnable runnable2 = ()->System.out.println("hello, world 3");
        runnable1.run();
        runnable2.run();
    }

    private static void example() {
        String[] a = {"a", "b", "c", "d", "e", "f"};
        final List<String> strings = Arrays.asList(a);
        strings.set(0, "A");
//        strings.add("g");

        System.out.println(strings);
//        final Spliterator<String> spliterator = strings.spliterator();
//        System.out.println(spliterator);
        for (String member : strings) {
            System.out.println(member);
        }
        System.out.println();

//        strings.forEach((member) ->System.out.println(member + ", "));
//        strings.forEach(System.out::println);
    }
}
