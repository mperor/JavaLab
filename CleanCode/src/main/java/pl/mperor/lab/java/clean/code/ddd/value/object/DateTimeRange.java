package pl.mperor.lab.java.clean.code.ddd.value.object;

import java.time.LocalDateTime;

record DateTimeRange(LocalDateTime start, LocalDateTime end) {

    DateTimeRange {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Date range start & end cannot be null!");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Date range start cannot be after end!");
        }
    }

    boolean isWithinRange(LocalDateTime dateTime) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }
}
