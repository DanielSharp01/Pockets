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
        return !date.toLocalDate().isEqual(lastOccurrence.toLocalDate()) && DAYS.between(lastOccurrence.toLocalDate(), date.toLocalDate()) % (everyX * 7) == 0
                && lastOccurrence.getDayOfWeek() == date.getDayOfWeek();
    }

    @Override
    public WeeklyRecurrence clone()
    {
        return new WeeklyRecurrence(getLastOccurrence(), getEveryX());
    }

    @Override
    public String toString() {
        return "Recurs every " + (everyX == 1 ? "" : everyX + " ") + "week" + (everyX > 1 ? "s" : "");
    }
}
