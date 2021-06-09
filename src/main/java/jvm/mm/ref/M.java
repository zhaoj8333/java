package jvm.mm.ref;

public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("M gc ");
    }
}
