package com.ubik.formation.userservice.controller;

import com.ubik.formation.userservice.converter.UserConverter;
import com.ubik.formation.userservice.dto.UserDto;
import com.ubik.formation.userservice.entity.User;
import com.ubik.formation.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Récupère tous les utilisateurs.
     * @return Liste des utilisateurs.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param id ID de l'utilisateur.
     * @return L'utilisateur.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Crée ou met à jour un utilisateur existant, dépendemment de la présence de l'id.
     * @param userDto DTO de l'utilisateur avec les nouvelles informations.
     * @return L'utilisateur mis à jour.
     */
    @PostMapping()
    @Operation(summary = "Créer ou mettre à jour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        UserDto user = userService.save(userDto);
        return ResponseEntity.ok(user);
    }

    /**
     * Supprimer un utilisateur.
     * @param id ID de l'utilisateur à supprimer.
     * @return Réponse vide avec statut 204 (No Content).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

