package pl.mperor.lab.java.idiom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecordsInDepthTest {

    @Test
    public void testCompactConstructorValidatesAndNormalizesName() {
        var team = new Team("  Falcons  ", List.of("Ann", "Bob"));
        Assertions.assertEquals("Falcons", team.name(), "the compact constructor strips surrounding whitespace");

        Assertions.assertThrows(NullPointerException.class, () -> new Team(null, List.of("Ann")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Team("   ", List.of("Ann")));
    }

    @Test
    public void testCompactConstructorDefensiveCopyOfMembers() {
        var mutableMembers = new ArrayList<>(List.of("Ann", "Bob"));
        var team = new Team("Falcons", mutableMembers);

        mutableMembers.add("Cid");
        Assertions.assertEquals(2, team.members().size(), "the record keeps its own copy, unaffected by later mutation of the source list");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> team.members().add("Dee"), "List.copyOf() returns an immutable list");
    }

    @Test
    public void testWitherReturnsModifiedCopyAndLeavesOriginalUnchanged() {
        var falcons = new Team("Falcons", List.of("Ann", "Bob"));
        var eagles = falcons.withName("Eagles");

        Assertions.assertEquals("Eagles", eagles.name());
        Assertions.assertEquals("Falcons", falcons.name(), "withName() returns a new instance; the original record is unchanged");
        Assertions.assertNotEquals(falcons, eagles);
        Assertions.assertSame(falcons.members(), eagles.members(), "the untouched component is shared, not re-copied");
    }

    record Team(String name, List<String> members) {
        Team {
            Objects.requireNonNull(name, "name must not be null");
            if (name.isBlank()) {
                throw new IllegalArgumentException("name must not be blank");
            }
            name = name.strip();
            members = List.copyOf(members);
        }

        Team withName(String newName) {
            return new Team(newName, members);
        }
    }

    @Test
    public void testArrayComponentBreaksGeneratedEqualsAndHashCode() {
        var badge1 = new Badge("A1", new byte[] {1, 2, 3});
        var badge2 = new Badge("A1", new byte[] {1, 2, 3});

        // Generated equals()/hashCode() compare array components by reference,
        // not content, so two "equal looking" badges are reported as different.
        Assertions.assertNotEquals(badge1, badge2);
        Assertions.assertArrayEquals(badge1.data(), badge2.data(), "the array contents are in fact identical");
    }

    record Badge(String id, byte[] data) {}
}
