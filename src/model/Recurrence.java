package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a recurrence which can tell when an event will occur
 */
public abstract class Recurrence implements Cloneable {

    /**
     * Last occurrence to count from
     */
    protected LocalDateTime lastOccurrence;

    /**
     * Will only occur every X units of time defined by the implementing class
     */
    protected int everyX;

    /**
     * @param lastOccurrence Last occurrence to count from
     * @param everyX Will only occur every X units of time defined by the implementing class
     */
    public Recurrence(LocalDateTime lastOccurrence, int everyX) {
        this.lastOccurrence = lastOccurrence;
        this.everyX = everyX;
    }

    /**
     * @param lastOccurrence Last occurrence to count from
     */
    public Recurrence(LocalDateTime lastOccurrence) {
        this(lastOccurrence, 1);
    }

    /**
     * @return Last occurrence to count from
     */
    public LocalDateTime getLastOccurrence() {
        return lastOccurrence;
    }

    /**
     * @param lastOccurrence Last occurrence to count from
     */
    public void setLastOccurrence(LocalDateTime lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    /**
     * @return Will only occur every X units of time defined by the implementing class
     */
    public int getEveryX() {
        return everyX;
    }

    /**
     * @param everyX Will only occur every X units of time defined by the implementing class
     */
    public void setEveryX(int everyX) {
        this.everyX = everyX;
    }

    /**
     * Tells if a specific date is an occurrence according to the recurrence rules
     * @param date The specific date to check
     * @return Whether the date is an occurrence
     */
    public abstract boolean isOccurrence(LocalDate date);

    @Override
    public abstract Recurrence clone();
}
