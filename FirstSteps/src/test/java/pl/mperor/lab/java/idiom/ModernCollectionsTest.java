package pl.mperor.lab.java.idiom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModernCollectionsTest {

    @Test
    public void testListOfIsImmutableAndNullHostile() {
        var names = List.of("Ann", "Bob");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> names.add("Cid"));
        Assertions.assertThrows(NullPointerException.class, () -> List.of("Ann", null), "List.of() rejects null elements outright");
    }

    @Test
    public void testListCopyOfTakesAnImmutableSnapshot() {
        var mutable = new ArrayList<>(List.of("Ann", "Bob"));
        var snapshot = List.copyOf(mutable);

        mutable.add("Cid");
        Assertions.assertEquals(2, snapshot.size(), "copyOf() took a snapshot, unaffected by later changes to the source list");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> snapshot.add("Dee"));
    }

    @Test
    public void testUnmodifiableListIsALiveViewNotASnapshot() {
        var mutable = new ArrayList<>(List.of("Ann", "Bob"));
        var view = Collections.unmodifiableList(mutable);

        mutable.add("Cid");
        Assertions.assertEquals(3, view.size(), "unmodifiableList() wraps the same backing list, so it reflects later changes");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> view.add("Dee"), "only direct mutation through the view is blocked");
    }

    @Test
    public void testStreamToListIsImmutableWhileCollectorsToListIsMutable() {
        var fromStream = Stream.of("Ann", "Bob").toList();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> fromStream.add("Cid"), "Stream.toList() guarantees an unmodifiable result");

        // Collectors.toList() makes no mutability guarantee; it happens to return
        // a mutable ArrayList today, which is exactly why Stream.toList() exists.
        var fromCollector = Stream.of("Ann", "Bob").collect(Collectors.toList());
        fromCollector.add("Cid");
        Assertions.assertEquals(3, fromCollector.size());
    }

    @Test
    public void testSequencedCollectionGetFirstGetLastAndReversedOnList() {
        List<String> ranking = List.of("Gold", "Silver", "Bronze");

        Assertions.assertEquals("Gold", ranking.getFirst());
        Assertions.assertEquals("Bronze", ranking.getLast());
        Assertions.assertEquals(List.of("Bronze", "Silver", "Gold"), ranking.reversed(), "reversed() returns a view in reverse encounter order");
    }

    @Test
    public void testSequencedCollectionOnLinkedHashSetPreservesInsertionOrder() {
        var visited = new LinkedHashSet<>(List.of("Warsaw", "Berlin", "Paris"));

        Assertions.assertEquals("Warsaw", visited.getFirst());
        Assertions.assertEquals("Paris", visited.getLast());
        Assertions.assertEquals(List.copyOf(visited.reversed()), List.of("Paris", "Berlin", "Warsaw"));
    }

    @Test
    public void testSequencedMapOnLinkedHashMapExposesFirstAndLastEntries() {
        var scores = new LinkedHashMap<String, Integer>();
        scores.put("Ann", 10);
        scores.put("Bob", 20);
        scores.put("Cid", 30);

        Assertions.assertEquals("Ann", scores.firstEntry().getKey());
        Assertions.assertEquals("Cid", scores.lastEntry().getKey());
        Assertions.assertEquals(List.of("Cid", "Bob", "Ann"), List.copyOf(scores.reversed().keySet()), "reversed() on a SequencedMap flips iteration order, not insertion order");
    }
}
