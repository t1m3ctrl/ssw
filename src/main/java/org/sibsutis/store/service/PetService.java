package org.sibsutis.store.service;

import org.sibsutis.store.exceptions.PetNotFoundException;
import org.sibsutis.store.model.Pet;
import org.sibsutis.store.model.Status;
import org.sibsutis.store.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet findById(Long id) throws PetNotFoundException {
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Pet not found"));
    }

    public Pet updateById(Long id, String name, Status status) throws PetNotFoundException {
        Pet pet = findById(id);
        pet.setName(name);
        pet.setStatus(status);
        return petRepository.save(pet);
    }

    public Pet update(Pet pet) throws PetNotFoundException {
        findById(pet.getId());
        Pet petOld;
        petOld = pet;
        return petRepository.save(petOld);
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet delete(Long id) throws PetNotFoundException {
        Pet pet = findById(id);
        petRepository.delete(pet);
        return pet;
    }


}
