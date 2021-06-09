package lang.dt;

public class InstanceOf {
    public static void main(String[] args) {
        int i = 0;
        // java: unexpected type
//        System.out.println(i instanceof Integer);

//        System.out.println(i instanceof Object);

        Integer integer = new Integer(0);
        System.out.println(integer instanceof Integer);

        System.out.println(null instanceof Object);
    }
}
