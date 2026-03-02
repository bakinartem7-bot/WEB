package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.Searchable;
import java.util.UUID;

public final class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String text;

    public Article(UUID id, String title, String text) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Заголовок не может быть пустым");
        }
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Override
    public String getId() {  // ← Изменён тип возврата: UUID → String
        return id.toString();  // ← Преобразуем UUID в строку
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return title + " " + text;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String toString() {
        return title + " " + text;
    }
}
