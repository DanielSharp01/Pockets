package model.recurrences;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes every nth occurrence of it's internal Recurrence
 */
public class NthRecurrence extends Recurrence {

    /**
     * Internal recurrence whose every nth occurrence we take
     */
    private Recurrence internalRecurrence;
    /**
     * Takes every nth occurrence only
     */
    private int n;

    /**
     * @param internalRecurrence Internal recurrence whose every nth occurrence we take
     * @param n Takes every nth occurrence only
     */
    public NthRecurrence(Recurrence internalRecurrence, int n) {
        this.internalRecurrence = internalRecurrence;
        this.n = n;
    }

    @Override
    public List<LocalDate> getOccurrencesSince(LocalDate date) {
        List<LocalDate> occurrences = new ArrayList<>();
        List<LocalDate> internalOccurrences = internalRecurrence.getOccurrencesSince(date);
        for (int i = n - 1; i < internalOccurrences.size(); i += n)
        {
            occurrences.add(internalOccurrences.get(i));
        }
        return occurrences;
    }
}
