package lang.ds.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<String, Integer> hs = new HashMap<>();
        hs.put("a", 1);
        hs.put("b", 2);
        hs.put("c", 3);

        System.out.println(hs);
    }


}
