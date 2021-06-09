package design.templ;

public class Cricket extends Game {
    @Override
    void init() {
        System.out.println("cricket init");
    }

    @Override
    void star() {
        System.out.println("cricket star");
    }

    @Override
    void end() {
        System.out.println("cricket end");
    }

    public static void main(String[] args) {
        Cricket c = new Cricket();
        c.play();
    }
}
