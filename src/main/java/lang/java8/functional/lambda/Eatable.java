package lang.java8.functional.lambda;

@FunctionalInterface
public interface Eatable {
    void eat();


    /**
     * 如果interface中某个抽象方法复写了Object的public方法, 此时 接口的抽象方法计数不会 + 1
     */
    @Override
    String toString();

}
