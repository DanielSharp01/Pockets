package model.filters;

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
}
