package jvm.clazz.loading.ce;

public class MySample {
    public MySample() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println("MySample is loaded by : " + classLoader);
        final MyCat myCat = new MyCat();
    }

//    private static MyCat myCat = null;
}
