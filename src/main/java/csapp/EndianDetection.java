package csapp;

//import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class EndianDetection {
//    public static void main(String[] args) {
//        try {
//            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//            theUnsafe.setAccessible(true);
//            Unsafe o = (Unsafe) theUnsafe.get(null);
//            long l = o.allocateMemory(8);
//            o.putLong(l, 0x102030405060708L);
//            byte b = o.getByte(l);
//            System.out.println(b);
//            switch (b) {
//                case 0x01:
//                    System.out.println("大端序");
//                case 0x08:
//                    System.out.println("小端序");
//                default:
//                    assert false;
//            }
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
