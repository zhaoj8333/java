package lang.java8.functional;

public class Tom implements People {
    @Override
    public String say(String msg) {
        return "hello, tom " + msg;
    }

    public static void main(String[] args) {
        final Tom tom = new Tom();
        System.out.println(tom.say("撒大家快乐飞"));
    }
}
