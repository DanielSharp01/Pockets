package model;

import java.time.LocalDate;

/**
 * Represents a never occurring recurrence
 */
public class NullRecurrence extends Recurrence {

    public NullRecurrence() {
        super(null);
    }

    @Override
    public boolean isOccurrence(LocalDate date) {
        return false;
    }

    @Override
    public NullRecurrence clone()
    {
        return new NullRecurrence();
    }

    @Override
    public String toString() {
        return "";
    }
}
