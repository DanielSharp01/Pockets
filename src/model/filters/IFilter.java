package model.filters;

import javafx.collections.transformation.FilteredList;

import java.util.Collection;

/**
 * Filters a collection given specific information
 */
public interface IFilter {
    /**
     * Returns a filtered collection
     * @param filterables Filterable collection
     * @return A collection of filtered items from the filterables
     */
    Collection<? extends IFilterable> filter(Collection<? extends IFilterable> filterables);

    /**
     * Sets the pradicate of a FilteredList
     * @param filterables FilteredList to set the predicate of
     */
    void setFilteredListPredicate(FilteredList<? extends IFilterable> filterables);
}
