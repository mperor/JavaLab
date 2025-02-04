package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

sealed interface MediaPlayerState permits PausedState, PlayingState, StoppedState {

    void pressPlay(MediaPlayer player);

    void pressPause(MediaPlayer player);

    void pressStop(MediaPlayer player);
}