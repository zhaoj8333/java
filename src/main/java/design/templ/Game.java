package design.templ;

public abstract class Game {
    abstract void init();
    abstract void star();
    abstract void end();

    public final void play() {
        init();
        star();
        end();
    }
}
