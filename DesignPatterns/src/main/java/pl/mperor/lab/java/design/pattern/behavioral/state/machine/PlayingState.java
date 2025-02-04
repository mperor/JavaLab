package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

record PlayingState() implements MediaPlayerState {

    @Override
    public void pressPlay(MediaPlayer player) {
        System.out.println("▶ Already playing!");
    }

    @Override
    public void pressPause(MediaPlayer player) {
        System.out.println("⏸ Pausing music...");
        player.setState(new PausedState());
    }

    @Override
    public void pressStop(MediaPlayer player) {
        System.out.println("⏹ Stopping music...");
        player.setState(new StoppedState());
    }
}