package pl.mperor.lab.java.design.pattern.behavioral.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.mperor.lab.java.design.pattern.behavioral.iterator.Worm.WormColor;

public class WormSegmentIteratorTest {

    @Test
    public void testWormHasIterableSegments() {
        var scientist = new Scientist();
        var worms = scientist.getWorms();
        var popularityByWormColor = worms.stream()
                .map(scientist::findDominantColorOfWorm)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var dominantColorInWorms = popularityByWormColor.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();

        Assertions.assertEquals(WormColor.GREEN, dominantColorInWorms);
    }

    private record Scientist() {

        private List<Worm> getWorms() {
            return List.of(
                    new Worm(WormColor.RED, WormColor.RED, WormColor.GREEN, WormColor.GREEN, WormColor.RED, WormColor.GREEN, WormColor.BLACK, WormColor.BLACK, WormColor.GREEN, WormColor.WHITE, WormColor.RED, WormColor.WHITE, WormColor.BLUE, WormColor.WHITE, WormColor.WHITE, WormColor.BLACK, WormColor.RED, WormColor.BLACK, WormColor.GREEN, WormColor.GREEN, WormColor.WHITE, WormColor.GREEN, WormColor.BLACK, WormColor.WHITE),
                    new Worm(WormColor.GREEN, WormColor.RED, WormColor.GREEN, WormColor.GREEN, WormColor.WHITE, WormColor.BLACK, WormColor.GREEN, WormColor.GREEN, WormColor.BLUE, WormColor.WHITE, WormColor.BLACK, WormColor.WHITE, WormColor.GREEN, WormColor.WHITE, WormColor.GREEN, WormColor.BLUE, WormColor.BLUE, WormColor.RED, WormColor.WHITE, WormColor.BLACK),
                    new Worm(WormColor.RED, WormColor.BLUE, WormColor.GREEN, WormColor.WHITE, WormColor.GREEN, WormColor.BLUE, WormColor.WHITE, WormColor.BLACK, WormColor.RED, WormColor.WHITE, WormColor.GREEN, WormColor.RED, WormColor.RED, WormColor.BLUE, WormColor.GREEN, WormColor.BLUE),
                    new Worm(WormColor.GREEN, WormColor.RED, WormColor.GREEN, WormColor.BLACK, WormColor.RED, WormColor.BLACK, WormColor.BLACK, WormColor.GREEN, WormColor.RED, WormColor.WHITE, WormColor.WHITE, WormColor.GREEN, WormColor.BLUE, WormColor.RED, WormColor.RED, WormColor.WHITE, WormColor.GREEN, WormColor.BLUE, WormColor.BLUE),
                    new Worm(WormColor.BLACK, WormColor.BLACK, WormColor.GREEN, WormColor.BLUE, WormColor.BLUE, WormColor.BLACK, WormColor.GREEN, WormColor.GREEN, WormColor.RED, WormColor.GREEN, WormColor.BLUE, WormColor.BLUE, WormColor.BLUE, WormColor.BLUE, WormColor.RED, WormColor.GREEN, WormColor.BLUE, WormColor.BLUE, WormColor.BLACK, WormColor.WHITE, WormColor.BLUE),
                    new Worm(WormColor.RED, WormColor.RED, WormColor.GREEN, WormColor.GREEN, WormColor.RED, WormColor.RED, WormColor.RED, WormColor.GREEN, WormColor.BLACK, WormColor.RED, WormColor.WHITE, WormColor.GREEN, WormColor.BLUE, WormColor.GREEN, WormColor.BLUE, WormColor.RED, WormColor.BLACK, WormColor.RED),
                    new Worm(WormColor.BLACK, WormColor.BLUE, WormColor.BLUE, WormColor.WHITE, WormColor.RED, WormColor.GREEN, WormColor.RED, WormColor.RED, WormColor.WHITE, WormColor.RED, WormColor.BLACK, WormColor.BLACK, WormColor.WHITE, WormColor.BLACK, WormColor.RED, WormColor.BLACK, WormColor.WHITE, WormColor.BLACK, WormColor.GREEN),
                    new Worm(WormColor.RED, WormColor.BLUE, WormColor.WHITE, WormColor.GREEN, WormColor.RED, WormColor.GREEN, WormColor.GREEN, WormColor.BLACK, WormColor.WHITE, WormColor.BLACK, WormColor.RED, WormColor.BLUE, WormColor.GREEN, WormColor.BLACK, WormColor.GREEN)
            );
        }

        private WormColor findDominantColorOfWorm(Worm worm) {
            Map<WormColor, Integer> popularityByWormColor = new EnumMap<>(WormColor.class);
            for (Worm.Segment segment : worm) {
                popularityByWormColor.merge(segment.color(), 1, Integer::sum);
            }
            return popularityByWormColor.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow();
        }
    }
}