package lang.obj.hierarchy;

public class Parent {
    public Parent() {
    }

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected int age;

    public int workTime;

    /**
     * 父类与子类：
     *
     *   父类先进内存
     *   子类后进内存
     *
     *   类中的属性，成员变量 跟随对象进入到堆内存
     *   父类中的属性，跟随子类对象进入堆内存
     *
     *   局部变量跟随方法进入内存
     *   成员变量跟随对象进内存
     *
     *   栈： 专门运行方法
     *   堆： 专门new对象
     *
     *   方法区： 存的是类的信息，方法的信息
     *   负责加载字节码文件
     *
     *   方法被调用时，会从方法区出来，压栈运行，运行完毕出栈
     *      先进后出
     *
     *
     *
     */
    protected void say() {
        System.out.println("I am father");
    }

    public void walk() {
        System.out.println("parent walk");
    }

    public final void smoke() {
        System.out.println("父亲抽烟");
    }
}
