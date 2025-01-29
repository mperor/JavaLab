package pl.mperor.lab.java.design.pattern.behavioral.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class MessageObserverTest {

    @Test
    public void messagePublisherShouldAllowToFollowHisMessages() {
        var out = TestUtils.setTempSystemOut();

        var socialMediaAccount = new SocialMediaAccount();
        socialMediaAccount.follow(new Follower("👨 Adam"));
        socialMediaAccount.follow(new Follower("👩 Eva"));
        socialMediaAccount.follow(message -> System.out.printf("🤖 I'm scanning your message: %s%n", message));
        socialMediaAccount.publish("Hello 🙋 everybody!");

        var printedLines = out.lines();
        Assertions.assertEquals("👨 Adam received message: Hello 🙋 everybody!", printedLines.getFirst());
        Assertions.assertEquals("👩 Eva received message: Hello 🙋 everybody!", printedLines.getSecond());
        Assertions.assertEquals("🤖 I'm scanning your message: Hello 🙋 everybody!", printedLines.getThird());

        TestUtils.resetSystemOut();
    }
}