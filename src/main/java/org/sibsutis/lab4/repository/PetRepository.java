package org.sibsutis.lab4.repository;

import org.sibsutis.lab4.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PetRepository {
    private final Map<Long, Pet> pets = new HashMap<>();

    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    public Pet save(Pet pet) {
        pets.put(pet.getId(), pet);
        return pet;
    }

    public Pet delete(Long id) {
        return pets.remove(id);
    }
}
