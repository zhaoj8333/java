package jvm.bc.dp.subj;

public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("real subject");
    }
}
