package com.ubik.formation.bookservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class BookDto {

    @Schema(description = "Identifiant unique du livre", example = "1")
    private Long id;

    @Schema(description = "Titre du livre", example = "Investir pour être libre")
    @NotBlank
    private String title;

    @Schema(description = "Auteur du livre", example = "Mounir Laggoune")
    @NotBlank
    private String author;

    @Schema(description = "Catégorie du livre", example = "Fiction")
    @NotBlank
    private String category;

    @Schema(description = "Disponibilité du livre", example = "true")
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
