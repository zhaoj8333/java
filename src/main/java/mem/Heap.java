package mem;

import java.util.ArrayList;

public class Heap
{
    byte[] a = new byte[1024 * 100];
    
    public static void main(String[] args) throws InterruptedException
    {
        ArrayList<Heap> heapTest = new ArrayList<Heap>();

        while (true) {
            heapTest.add(new Heap());
            Thread.sleep(10);
        }
    }
}
