package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

record StoppedState() implements MediaPlayerState {

    @Override
    public void pressPlay(MediaPlayer player) {
        System.out.println("▶ Starting music...");
        player.setState(new PlayingState());
    }

    @Override
    public void pressPause(MediaPlayer player) {
        System.out.println("⏸ Can't pause! Music is stopped.");
    }

    @Override
    public void pressStop(MediaPlayer player) {
        System.out.println("⏹ Already stopped!");
    }
}