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
        return !date.isEqual(lastOccurrence) && DAYS.between(lastOccurrence, date) % everyX == 0;
    }

    @Override
    public DailyRecurrence clone()
    {
        return new DailyRecurrence(getLastOccurrence(), getEveryX());
    }
}
