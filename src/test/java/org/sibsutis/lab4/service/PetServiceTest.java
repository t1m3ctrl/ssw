package org.sibsutis.lab4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sibsutis.lab4.exceptions.PetNotFoundException;
import org.sibsutis.lab4.model.Pet;
import org.sibsutis.lab4.model.Status;
import org.sibsutis.lab4.repository.PetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Pet");
        pet.setStatus(Status.available);
    }

    @Test
    void TestFindById_PetFound() throws PetNotFoundException {
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.findById(1L);

        assertNotNull(foundPet);
        assertEquals(pet.getId(), foundPet.getId());
        assertEquals(pet.getName(), foundPet.getName());

        Mockito.verify(petRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testFindById_PetNotFound() {
        Mockito.when(petRepository.findById(1L)).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> petService.findById(1L));
        assertEquals("Pet not found", exception.getMessage());

        Mockito.verify(petRepository, Mockito.times(1)).findById(1L);
    }
}