package com.example.backend.post;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Post {

    private @Id @GeneratedValue Long id;
    private String title;
    private String description;

    Post() {
    }

    Post(String title, String description, int qtd) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title);
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + this.id + ", title='" + this.title + '\'' + ", description=" + this.description + '}';
    }
}