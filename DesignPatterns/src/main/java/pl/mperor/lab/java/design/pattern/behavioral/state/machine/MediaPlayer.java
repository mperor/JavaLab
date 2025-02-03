package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

public class MediaPlayer {

    private MediaPlayerState state;

    public MediaPlayer() {
        this.state = new StoppedState();
    }

    public void setState(MediaPlayerState state) {
        this.state = state;
    }

    public void pressPlay() {
        state.pressPlay(this);
    }

    public void pressPause() {
        state.pressPause(this);
    }

    public void pressStop() {
        state.pressStop(this);
    }
}