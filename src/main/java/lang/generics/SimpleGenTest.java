package lang.generics;

public class SimpleGenTest {
    public static void main(String[] args) {
        SimpleGen sg = new SimpleGen(new Integer(10));
        sg.printType();

        int i = (Integer) sg.getObj();
        System.out.println("i: " + i);

        System.out.println();
        SimpleGen sg1 = new SimpleGen("aaaaaa");
        sg1.printType();
        String str = (String)sg1.getObj();
        System.out.println("str = " + str);

        System.out.println();

        int[] arr = new int[]{1, 2, 3};
        SimpleGen sg2 = new SimpleGen(arr);
        sg2.printType();

//        System.out.println(sg2.getObj());

        Object a = sg2.getObj();
        System.out.println("--" + a.toString());
    }
}
