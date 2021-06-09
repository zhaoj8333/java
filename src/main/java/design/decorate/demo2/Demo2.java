package design.decorate.demo2;

public class Demo2 {
    public static void main(String[] args) {
        final SexyGirl sexyGirl = new SexyGirl(new PureGirl());
        sexyGirl.dance();
    }
}
