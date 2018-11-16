package model;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MONTHS;

public class MonthlyRecurrence extends Recurrence {

    public MonthlyRecurrence(LocalDateTime lastOccurrence, int everyX) {
        super(lastOccurrence, everyX);
    }

    public MonthlyRecurrence(LocalDateTime lastOccurrence) {
        super(lastOccurrence);
    }

    @Override
    public boolean isOccurrence(LocalDateTime date) {
        return !date.isEqual(lastOccurrence) && MONTHS.between(lastOccurrence, date) % everyX == 0;
    }

    @Override
    public MonthlyRecurrence clone()
    {
        return new MonthlyRecurrence(getLastOccurrence(), getEveryX());
    }
}