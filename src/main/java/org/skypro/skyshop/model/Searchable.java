package org.skypro.skyshop.model;

public interface Searchable {
    String getId();
    String getSearchTerm();
    String getContentType();
    String getName();

    default String getStringRepresentation() {
        return getName() + " — " + getContentType();
    }
}