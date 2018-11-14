package utils;

public class StringUtils {
    /**
     * Determines whether a string contains a set of strings ignoring case
     * @param haystack String to search in
     * @param needles Strings to search for
     * @return True if all needles are contained, false if at least one of them is not
     */
    public static boolean containsAll(String haystack, String[] needles)
    {
        haystack = haystack.toLowerCase();
        for (String needle : needles)
        {
            if (!haystack.contains(needle.toLowerCase())) return false;
        }

        return true;
    }
}
