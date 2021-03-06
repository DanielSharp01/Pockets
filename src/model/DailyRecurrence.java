package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Represents a daily recurrence with optional every X days property
 */
public class DailyRecurrence extends Recurrence {
    /**
     * @param lastOccurrence Last occurrence to count from
     * @param everyX Will only occur every X days
     */
    public DailyRecurrence(LocalDateTime lastOccurrence, int everyX) {
        super(lastOccurrence, everyX);
    }

    /**
     * @param lastOccurrence Last occurrence to count from
     */
    public DailyRecurrence(LocalDateTime lastOccurrence) {
        super(lastOccurrence);
    }

    @Override
    public boolean isOccurrence(LocalDate date) {
        return date.isAfter(lastOccurrence.toLocalDate()) && DAYS.between(lastOccurrence.toLocalDate(), date) % everyX == 0;
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
