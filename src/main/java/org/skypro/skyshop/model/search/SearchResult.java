package org.skypro.skyshop.model.search;

import org.skypro.skyshop.model.Searchable;

public final class SearchResult {
    private final String id;
    private final String name;
    private final String contentType;

    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getContentType() { return contentType; }

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
                searchable.getId(),
                searchable.getName(),
                searchable.getContentType()
        );
    }
}
