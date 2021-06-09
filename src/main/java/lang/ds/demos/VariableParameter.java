package lang.ds.demos;

public class VariableParameter {
    public static void main(String[] args) {
        test1("aaa", 1, 2, 3, 4, 5, 6, 7, 8);
    }

    private static void test1(String str, int ... arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
