package lang.obj.hierarchy;

public interface Hello {
    public abstract void say();
}

interface HelloWorld extends Hello {
    public default void say() {
        System.out.println("say ");
    }

    void hello();
}