package model;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class WeeklyRecurrence extends Recurrence {
    public WeeklyRecurrence(LocalDateTime lastOccurrence, int everyX) {
        super(lastOccurrence, everyX);
    }

    public WeeklyRecurrence(LocalDateTime lastOccurrence) {
        super(lastOccurrence);
    }

    @Override
    public boolean isOccurrence(LocalDateTime date) {
        return !date.isEqual(lastOccurrence) && DAYS.between(lastOccurrence, date) % (everyX * 7) == 0
                && lastOccurrence.getDayOfWeek() == date.getDayOfWeek();
    }
}
