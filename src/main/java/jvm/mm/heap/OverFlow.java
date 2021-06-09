package jvm.mm.heap;

import java.util.ArrayList;

public class OverFlow {
    public static void main(String[] args) {
        int i = 0;
        try {
            final ArrayList<Object> list = new ArrayList<>();
            String a = "hello 啥积分卡拉是克服了暗示法肯定了撒娇开饭啦数据库费啦是减肥快拉倒撒娇快疯了金坷垃三角峰快乐是骄傲的分开了大事记快乐撒风景阿喀琉斯附近的斯科拉就分开来大神积分撒打开了房间萨克拉对方就爱是快乐的房间困了就睡分开睡";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
            // java.lang.OutOfMemoryError: Java heap space
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(i);
        }
    }
}
