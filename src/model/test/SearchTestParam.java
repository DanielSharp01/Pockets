package model.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SearchTestParam {
    private String searchTerm;
    private Set<Integer> idsExpected;

    public SearchTestParam(String searchTerm, Integer[] idsExpected)
    {
        this.searchTerm = searchTerm;
        this.idsExpected = new HashSet<>();
        Collections.addAll(this.idsExpected, idsExpected);
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Set<Integer> getIdsExpected() {
        return idsExpected;
    }
}
