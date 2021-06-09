package lang.ds.number;

public class FloatConversion {
    public static void main(String[] args) {
//        binaryToDecimal();
        decimalToBinary();
    }

    private static void  decimalToBinary() {
        // 6.625
        double d = 6.625d;
        System.out.println(d);
    }

    private static void binaryToDecimal() {
        // 110.101
        float f = 123.221f;
        System.out.println(Float.toString(f));

    }
}
