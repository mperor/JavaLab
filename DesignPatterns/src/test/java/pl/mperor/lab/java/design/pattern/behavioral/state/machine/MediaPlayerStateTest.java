package pl.mperor.lab.java.design.pattern.behavioral.state.machine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class MediaPlayerStateTest {

    @Test
    public void shouldBehaveLikeStateMachine() {
        var out = TestUtils.setTempSystemOut();

        var player = new MediaPlayer();
        player.pressPlay();  // Stopped -> Playing
        player.pressPause(); // Playing -> Paused
        player.pressPlay();  // Paused -> Playing
        player.pressStop();  // Playing -> Stopped
        player.pressPause(); // Invalid

        Assertions.assertLinesMatch("""
                ▶ Starting music...
                ⏸ Pausing music...
                ▶ Resuming music...
                ⏹ Stopping music...
                ⏸ Can't pause! Music is stopped.
                """.lines(), out.lines().stream());

        TestUtils.resetSystemOut();
    }
}