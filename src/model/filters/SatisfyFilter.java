package model.filters;

import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Filters a collection of items by the condition that each item must have all the words in it somewhere
 */
public class SatisfyFilter implements IFilter {

    /**
     * Words that need to be in the filterable
     */
    private String[] words;

    /**
     * @param searchTerm Search term that gets chopped up into filtered words
     */
    public SatisfyFilter(String searchTerm)
    {
        words = searchTerm.split(" ");
    }

    @Override
    public Collection<? extends IFilterable> filter(Collection<? extends IFilterable> filterables)
    {
        ArrayList<IFilterable> ret = new ArrayList<>();
        for (IFilterable filterable : filterables)
        {
            if (passesFilter(filterable))
                ret.add(filterable);
        }

        return ret;
    }

    @Override
    public void setFilteredListPredicate(FilteredList<? extends IFilterable> filterables)
    {
        filterables.setPredicate(this::passesFilter);
    }

    /**
     * Determines whether a single filterable passes the filter
     * @param filterable Filterable to check
     * @return True if passes, false otherwise
     */
    private boolean passesFilter(IFilterable filterable)
    {
        Collection<String> filterableWords = filterable.getWords();
        for (String word : words)
        {
            if (!wordsContain(filterableWords, word))
                return false;
        }

        return true;
    }

    /**
     * Determines whether a single word is satisfied by a filterable word list
     * @param filterableWords Filterable words
     * @param word Word to check
     * @return True if satisfied, false otherwise
     */
    private boolean wordsContain(Collection<String> filterableWords, String word)
    {
        word = word.toLowerCase();
        for (String filterableWord : filterableWords)
        {
            if (filterableWord.toLowerCase().contains(word))
                return true;
        }

        return false;
    }
}
