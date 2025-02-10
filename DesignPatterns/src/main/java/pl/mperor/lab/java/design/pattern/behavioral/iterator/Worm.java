package pl.mperor.lab.java.design.pattern.behavioral.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Worm implements Iterable<Worm.Segment> {

    private final List<Segment> segments;

    Worm(WormColor... colors) {
        this.segments = Arrays.stream(colors).map(Segment::new).toList();
    }

    @Override
    public Iterator<Segment> iterator() {
        return segments.iterator();
    }

    record Segment(WormColor color) {
    }

    enum WormColor {
        BLUE,
        WHITE,
        RED,
        GREEN,
        PURPLE,
        GRAY,
        BLACK
    }
}
