package effective.create.foo;

class MyFoo implements IFoo {

    public static IFoo instance() {
        return new MyFoo();
    }

    @Override
    public void bar() {
        System.out.println("bar");
    }
}
