package model.filters;

import java.util.Collection;

/**
 * Exposes the words contained in this item
 */
public interface IFilterable  {
    /**
     * Gets the words occurring in this item
     * @return Words occurring in this item
     */
    Collection<String> getWords();
}
