package effective.create;

import java.math.BigInteger;
import java.util.Random;

public class StaticFacMethod {
    public static void main(String[] args) {
//        cacheValueAndHasName();

        NonPublicClass nonPublicClass = new NonPublicClass();
        System.out.println(nonPublicClass);
        NonPublicClass instance = NonPublicClass.getInstance();
        System.out.println(instance);
    }

    /**
     * static methods has a name
     * can add customized logic to instantiate a class
     */
    private static void cacheValueAndHasName() {
        Boolean aFalse = Boolean.valueOf("false");
        System.out.println(aFalse);

        BigInteger bigInteger = BigInteger.probablePrime(10, new Random());
        System.out.println(bigInteger);
    }
}
