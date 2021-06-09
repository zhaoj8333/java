package lang.ds.collections;

import java.util.*;

public class CollectionsUtil {
    public static void main(String[] args) {
        Person a = new Person("a", "男", 10);
        Person b = new Person("b", "女", 20);
        Person c = new Person("c", "女", 30);

//        testEmpty();

        ArrayList<Person> people = new ArrayList<>();
        people.add(a);
        people.add(b);
        people.add(c);
        System.out.println(people);

        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });
        System.out.println(people);
        Collections.reverse(people);
        System.out.println(people);
    }

    private static void testEmpty() {
        Set<Person> peopleSet = Collections.emptySet();
        System.out.println(peopleSet);

//        peopleSet.add(person);
//        System.out.println();

        List<Person> peopleList = Collections.emptyList();
//        peopleList.add(person1);
//        System.out.println(peopleList);

        Map<String, Person> peopleMap = Collections.emptyMap();
//        peopleMap.put("a", person);
    }
}
