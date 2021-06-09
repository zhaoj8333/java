package jvm.mm.gc;

import java.util.ArrayList;

public class HeapTest {
    byte[] a = new byte[1024 * 100];

    public static void main(String[] args) {
        ArrayList<HeapTest> tests = new ArrayList<>();
        while (true) {
            tests.add(new HeapTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
