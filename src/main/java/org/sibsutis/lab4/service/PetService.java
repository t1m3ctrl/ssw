package org.sibsutis.lab4.service;

import org.sibsutis.lab4.exceptions.PetNotFoundException;
import org.sibsutis.lab4.model.Pet;
import org.sibsutis.lab4.model.Status;
import org.sibsutis.lab4.repository.PetRepository;
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
        findById(id);
        return petRepository.delete(id);
    }


}
