package csapp;

public class FloatMathProp {
    public static void main(String[] args) {
        associativeOfFloat();
    }

    private static void associativeOfFloat() {
        float a = (float) ((3.14 + 1e20) - 1e20);
        System.out.println(3.14 + 1e20);
        System.out.println(a);  // 0.0

        System.out.println(1e20);

        float b = (float) ((3.14 + (1e20 - 1e20)));
        System.out.println(b);  // 3.14

    }
}
