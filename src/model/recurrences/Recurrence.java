package model.recurrences;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a recurrence which can tell when an event will occur
 */
public abstract class Recurrence {
    /**
     * Tells when will have an event occurred since a specific date
     * @param date The specific date
     * @return List of occurrences in order
     */
    public abstract List<LocalDate> getOccurrencesSince(LocalDate date);
}
