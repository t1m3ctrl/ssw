package org.sibsutis.store.controller;

import org.sibsutis.store.exceptions.PetNotFoundException;
import org.sibsutis.store.model.Pet;
import org.sibsutis.store.model.Status;
import org.sibsutis.store.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/")
public class PetController implements PetControllerDesc {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PutMapping("/pet")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) throws PetNotFoundException {
        return ResponseEntity.ok(petService.update(pet));
    }

    @PostMapping("/pet")
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.create(pet));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable long petId) throws PetNotFoundException {
        return ResponseEntity.ok(petService.findById(petId));
    }

    @PostMapping("pet/{petId}")
    public ResponseEntity<Pet> updatePetById(@PathVariable long petId, @RequestParam String name, @RequestParam Status status) throws PetNotFoundException {
        return ResponseEntity.ok(petService.updateById(petId, name, status));
    }

    @DeleteMapping("pet/{petId}")
    public ResponseEntity<Pet> deletePet(@PathVariable long petId, @RequestHeader(value = "api_key", required = false) String apiKey) throws PetNotFoundException {
        return ResponseEntity.ok(petService.delete(petId));
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> handlePetNotFoundException(PetNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(400).body("Invalid request body: " + ex.getMessage());
    }
}
