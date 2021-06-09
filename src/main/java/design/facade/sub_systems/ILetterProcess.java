package design.facade.sub_systems;

public interface ILetterProcess {
    void writeContext(String context);
    void fillEnvelop(String address);
    void sendLetter();
}
