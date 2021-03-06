package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MONTHS;

/**
 * Represents a monthly recurrence with optional every X days property
 */
public class MonthlyRecurrence extends Recurrence {

    /**
     * @param lastOccurrence Last occurrence to count from
     * @param everyX Will only occur every X months
     */
    public MonthlyRecurrence(LocalDateTime lastOccurrence, int everyX) {
        super(lastOccurrence, everyX);
    }

    /**
     * @param lastOccurrence Last occurrence to count from
     */
    public MonthlyRecurrence(LocalDateTime lastOccurrence) {
        super(lastOccurrence);
    }

    @Override
    public boolean isOccurrence(LocalDate date) {
        if (date.isAfter(lastOccurrence.toLocalDate()) && MONTHS.between(lastOccurrence.toLocalDate(), date) % everyX == 0)
        {
            int maxDays = date.getMonth().length(date.isLeapYear());
            if (lastOccurrence.getDayOfMonth() > date.getDayOfMonth() && date.getDayOfMonth() == maxDays)
            {
                return true;
            }

            if (lastOccurrence.getDayOfMonth() == date.getDayOfMonth())
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public MonthlyRecurrence clone()
    {
        return new MonthlyRecurrence(getLastOccurrence(), getEveryX());
    }

    @Override
    public String toString() {
        return "Recurs every " + (everyX == 1 ? "" : everyX + " ") + "month" + (everyX > 1 ? "s" : "");
    }
}