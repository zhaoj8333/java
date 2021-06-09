package design.facade.sub_systems;

public class LetterProcessImpl implements ILetterProcess {
    @Override
    public void writeContext(String context) {
        System.out.println("写信");
    }

    @Override
    public void fillEnvelop(String address) {
        System.out.println("填写信封");
    }

    @Override
    public void sendLetter() {
        System.out.println("邮寄信件");
    }
}
