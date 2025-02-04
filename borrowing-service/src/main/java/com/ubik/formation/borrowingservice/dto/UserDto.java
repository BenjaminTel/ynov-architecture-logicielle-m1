package com.ubik.formation.borrowingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @Schema(description = "L'ID unique de l'utilisateur", example = "1")
    private Long id;

    @Schema(description = "Le nom de l'utilisateur", example = "Mohamed Aijou")
    @NotBlank
    private String name;

    @Email(message = "Email should be valid")
    @Schema(description = "L'adresse email de l'utilisateur", example = "Mohamed.aijou@example.com")
    private String email;

    @Schema(description = "Le type d'abonnement de l'utilisateur", example = "REGULAR")
    @NotBlank
    private String membershipType;

    @Schema(description = "Indique si l'utilisateur a atteint son quota d'emprunt", example = "false")
    private boolean isLocked;

    @Schema(description = "Le nombre maximum d'emprunts que l'utilisateur peut effectuer, 5 pour les REGULAR, 7 pour les PRENIUM", example = "5")
    private Integer nombreMaxEmprunt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Integer getNombreMaxEmprunt() {
        return nombreMaxEmprunt;
    }

    public void setNombreMaxEmprunt(Integer nombreMaxEmprunt) {
        this.nombreMaxEmprunt = nombreMaxEmprunt;
    }
}