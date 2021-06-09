package effective.create.foo;

public abstract class Foos {
    public static IFoo createFoo() {
        return MyFoo.instance();
    }

}
