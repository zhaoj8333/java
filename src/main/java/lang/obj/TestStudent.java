package lang.obj;

import lang.maker_interface.serialize.SerializableStudent;

public class TestStudent {
    public static void main(String[] args) {
        SerializableStudent st = new SerializableStudent("张三", 29, "成都", 89);
        System.out.println(st.toString());
    }
}
