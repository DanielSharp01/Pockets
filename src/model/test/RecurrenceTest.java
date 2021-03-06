package model.test;

import model.DailyRecurrence;
import model.MonthlyRecurrence;
import model.NullRecurrence;
import model.WeeklyRecurrence;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecurrenceTest {
    @Test
    public void testNullRecurrence()
    {
        assertFalse(new NullRecurrence().isOccurrence(LocalDate.of(2018, 5, 6)));
    }

    @Test
    public void testEveryDay()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        DailyRecurrence recurrence = new DailyRecurrence(date);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 0; i < 50; i++) {
            date = date.plusDays(1);
            assertTrue(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testEveryWeek()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        WeeklyRecurrence recurrence = new WeeklyRecurrence(date);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 0; i < 50; i++) {
            date = date.plusWeeks(1);
            assertTrue(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testWeekNonFullJump()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 31, 0, 0, 0);
        WeeklyRecurrence recurrence = new WeeklyRecurrence(date);
        assertTrue(recurrence.isOccurrence(LocalDate.of(2018, 6, 7)));
        assertFalse(recurrence.isOccurrence(LocalDate.of(2018, 6, 1)));
    }

    @Test
    public void testEveryMonth()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        MonthlyRecurrence recurrence = new MonthlyRecurrence(date);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 0; i < 50; i++) {
            date = date.plusMonths(1);
            assertTrue(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testMonth31st()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 31, 6, 0, 0);
        MonthlyRecurrence recurrence = new MonthlyRecurrence(date);
        assertTrue(recurrence.isOccurrence(LocalDate.of(2018, 6, 30)));
        assertFalse(recurrence.isOccurrence(LocalDate.of(2018, 6, 1)));
    }

    @Test
    public void testEveryOtherDay()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        DailyRecurrence recurrence = new DailyRecurrence(date, 2);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 1; i < 50; i++) {
            date = date.plusDays(1);
            if (i % 2 == 0) assertTrue(recurrence.isOccurrence(date.toLocalDate()));
            else assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testEveryOtherWeek()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        WeeklyRecurrence recurrence = new WeeklyRecurrence(date, 2);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 1; i < 50; i++) {
            date = date.plusWeeks(1);
            if (i % 2 == 0) assertTrue(recurrence.isOccurrence(date.toLocalDate()));
            else assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testEveryOtherMonth()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        MonthlyRecurrence recurrence = new MonthlyRecurrence(date, 2);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        for (int i = 1; i < 50; i++) {
            date = date.plusMonths(1);
            if (i % 2 == 0) assertTrue(recurrence.isOccurrence(date.toLocalDate()));
            else assertFalse(recurrence.isOccurrence(date.toLocalDate()));
        }
    }

    @Test
    public void testMultipleOccurrenceChecks()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        DailyRecurrence recurrence = new DailyRecurrence(date);
        date = date.plusDays(1);
        assertTrue(recurrence.isOccurrence(date.toLocalDate()));
        recurrence.setLastOccurrence(date);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
    }

    @Test
    public void testDifferentTime()
    {
        LocalDateTime date = LocalDateTime.of(2018, 5, 6, 0, 0, 0);
        DailyRecurrence recurrence = new DailyRecurrence(date);
        date = date.plusDays(1).plusHours(4).plusMinutes(2).plusSeconds(3);
        assertTrue(recurrence.isOccurrence(date.toLocalDate()));
        recurrence.setLastOccurrence(date);
        assertFalse(recurrence.isOccurrence(date.toLocalDate()));
    }
}