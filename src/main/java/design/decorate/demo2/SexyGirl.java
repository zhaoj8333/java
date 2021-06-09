package design.decorate.demo2;

public class SexyGirl implements Girl {

    private Girl girl;

    public SexyGirl(Girl girl) {
        this.girl = girl;
    }

    public SexyGirl() {
    }

    @Override
    public void dance() {
        System.out.println("sexy girl");
        if (this.girl != null) {
            this.girl.dance();
        }
    }
}
