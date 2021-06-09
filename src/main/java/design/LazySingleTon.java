package design;

public class LazySingleTon {
//    private static LazySingleTon singleTon = null;
    private static volatile LazySingleTon singleTon = null;
    private LazySingleTon() {

    }

    /**
    public static LazySingleTon getInstance() {
        if (singleTon == null) {
            singleTon = new LazySingleTon();
        }
        return singleTon;
    }
    */

    /**
     * 加锁 保证安全性
     */
    public static synchronized LazySingleTon getInstance() {
        if (singleTon == null) {
            singleTon = new LazySingleTon();
        }

        return singleTon;
    }
}
