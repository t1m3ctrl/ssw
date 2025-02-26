package org.sibsutis.lab4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sibsutis.lab4.model.Pet;
import org.sibsutis.lab4.model.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface PetControllerDesc {

    @Operation(summary = "Update an existing pet", description = "Update an existing pet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pet data"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    ResponseEntity<Pet> updatePet(@RequestBody Pet pet) throws Exception;

    @Operation(summary = "Add a new pet", description = "Add a new pet to the store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<Pet> createPet(@RequestBody Pet pet) throws Exception;

    @Operation(summary = "Find pet by ID", description = "Returns a single pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "400", description = "Invalid pet ID supplied"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    ResponseEntity<Pet> getPet(@PathVariable long petId) throws Exception;

    @Operation(summary = "Update pet with form data", description = "Update a pet's name and status using form data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<Pet> updatePetById(@PathVariable long petId, @RequestParam String name, @RequestParam Status status) throws Exception;

    @Operation(summary = "Delete a pet", description = "Delete a pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pet id")
    })
    ResponseEntity<Pet> deletePet(@PathVariable long petId, @RequestHeader(value = "api_key", required = false) String apiKey) throws Exception;
}
