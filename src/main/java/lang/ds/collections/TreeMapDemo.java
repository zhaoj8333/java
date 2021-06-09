package lang.ds.collections;

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        final TreeMap<Object, Object> tm = new TreeMap<>();
        tm.put("1", 1);
        tm.put("2", 2);
        tm.put("3", 3);
        tm.put("4", 4);
        tm.put("5", 5);
        tm.put("6", 6);

        System.out.println(tm);
        System.out.println(tm.firstKey());

        final SortedMap<Object, Object> a = tm.tailMap("3");
        System.out.println(a);
        System.out.println(a.firstKey());
    }
}
