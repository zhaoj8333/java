package lang.generics;

import java.util.Date;

/**
 * 元组类库
 *  元祖与list一样，都用于数据存储，包含多个数据
 *  但是列表只能存储相同类型的数据，而元祖可以存储不同的数据类型，并且可以无限扩展
 *
 */

public class Tuple {
    public static void main(String[] args) {
        TwoTuple<Integer, String> twoT = new TwoTuple<Integer, String>(100, "aaaaaaaa");
        System.out.println(twoT);
        System.out.println("===========================");

        ThreeTuple<Integer, String, Date> threeT = new ThreeTuple<Integer, String, Date>(200, "bbbbbbb", new Date());
        System.out.println(threeT);
        System.out.println("===========================");

        ThreeTuple<Long, String, Integer> threeT1 = new ThreeTuple<Long, String, Integer>(1000L, "ccccc", 900);
        System.out.println(threeT1);
    }
}

class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        first  = a;
        second = b;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    private final C three;

    public ThreeTuple(A a, B b, C c) {
        super(a, b);
        three = c;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + three + ")";
    }
}
