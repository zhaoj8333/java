package lang.java8.functional;

public class CustomSqrt implements Formula {

    @Override
    public double sqrt(int a) {
        return 0;
    }

    @Override
    public double calculate(int a) {
        return sqrt(a) * 10;
    }

    public static void main(String[] args) {
        final Formula formula = new Formula() {

            @Override
            public double calculate(int a) {
                return a * a * 10;
            }
        };

        System.out.println(formula.calculate(5));

        final CustomSqrt customSqrt = new CustomSqrt();
        final double calculate = customSqrt.calculate(5);
        System.out.println(calculate);

    }
}
