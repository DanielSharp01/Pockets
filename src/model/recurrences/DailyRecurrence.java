package model.recurrences;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyRecurrence extends Recurrence {

    @Override
    public List<LocalDate> getOccurrencesSince(LocalDate date) {
        List<LocalDate> occurrences = new ArrayList<>();
        LocalDate today = LocalDate.now();

        date.plusDays(1);
        while (today.compareTo(date) >= 0)
        {
            occurrences.add(date);
            date.plusDays(1);
        }

        return occurrences;
    }
}
