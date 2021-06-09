package lang.reflection.d1.stati;

public class Proxy extends Subject {
    private RealSubject real;

    @Override
    public void Request() {
        if (null == real) {
            real = new RealSubject();
        }
        real.Request();
    }
}
