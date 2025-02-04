package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

record PausedState() implements MediaPlayerState {

    @Override
    public void pressPlay(MediaPlayer player) {
        System.out.println("▶ Resuming music...");
        player.setState(new PlayingState());
    }

    @Override
    public void pressPause(MediaPlayer player) {
        System.out.println("⏸ Already paused!");
    }

    @Override
    public void pressStop(MediaPlayer player) {
        System.out.println("⏹ Stopping music...");
        player.setState(new StoppedState());
    }
}