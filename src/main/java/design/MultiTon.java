package design;

import java.util.ArrayList;
import java.util.Random;

public class MultiTon {
    // 在某个程序运行期间，只能产生固定的几个对象，不能多，不能少
    // 扑克类 54个
    // 麻将
    // 三原色
    private MultiTon() {}
    private static final int count = 3;
    private static ArrayList l = new ArrayList<>();

    static {
        for (int i = 0; i < count; i++) {
            MultiTon mt = new MultiTon();
            l.add(mt);
        }
    }

    public static MultiTon getMultiton() {
        Random random = new Random();
        int index = random.nextInt(count);

        return (MultiTon) l.get(index);
    }
}
