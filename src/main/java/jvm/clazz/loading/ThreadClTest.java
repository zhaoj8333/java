package jvm.clazz.loading;

public class ThreadClTest implements Runnable {
    private Thread thread;

    public ThreadClTest() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        final ClassLoader cl = this.thread.getContextClassLoader();
        this.thread.setContextClassLoader(cl);

        System.out.println("Class  : " + cl);
        System.out.println("Parent : " + cl.getParent().getClass());
    }

    public static void main(String[] args) {
        new ThreadClTest();
    }
}
