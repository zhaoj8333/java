package design.decorate.demo2;

public class PureGirl implements Girl{

    private Girl girl;

    public PureGirl(Girl girl) {
        this.girl = girl;
    }

    public PureGirl() {
    }

    @Override
    public void dance() {
        System.out.println("pure girl");
        if (this.girl != null) {
            this.girl.dance();
        }
    }
}
