package lang.obj;

/**
 * java数组 为Object的子类
 */
public class ArrayAndObject {
    public static void main(String[] args) {
        int[] ints = new int[3];
        System.out.println(ints instanceof Object);   // true
        System.out.println(ints.equals(null));
        System.out.println(ints.length);
        System.out.println(ints.toString());
        System.out.println(ints.getClass());    // class [I
    }
}
