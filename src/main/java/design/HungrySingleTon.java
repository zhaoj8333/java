package design;

public class HungrySingleTon {
    // 饿汉式: 类一旦加载，就赶紧new对象
    private static HungrySingleTon singleTon = new HungrySingleTon();
    private HungrySingleTon() {

    }
    public static HungrySingleTon getInstance() {
        return singleTon;
    }
}
