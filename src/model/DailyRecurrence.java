package model;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Represents a daily recurrence with optional every X days property
 */
public class DailyRecurrence extends Recurrence {
    public DailyRecurrence(LocalDateTime lastOccurrence, int everyX) {
        super(lastOccurrence, everyX);
    }

    public DailyRecurrence(LocalDateTime lastOccurrence) {
        super(lastOccurrence);
    }

    @Override
    public boolean isOccurrence(LocalDateTime date) {
        return !date.toLocalDate().isEqual(lastOccurrence.toLocalDate()) && DAYS.between(lastOccurrence.toLocalDate(), date.toLocalDate()) % everyX == 0;
    }

    @Override
    public DailyRecurrence clone()
    {
        return new DailyRecurrence(getLastOccurrence(), getEveryX());
    }

    @Override
    public String toString() {
        return "Recurs every " + (everyX == 1 ? "" : everyX + " ") + "day" + (everyX > 1 ? "s" : "");
    }
}
