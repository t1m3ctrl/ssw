package org.sibsutis.store.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sibsutis.store.exceptions.PetNotFoundException;
import org.sibsutis.store.model.Pet;
import org.sibsutis.store.model.Status;
import org.sibsutis.store.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("store")
public class PetServiceDataBaseTest {

    private Pet pet;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @BeforeAll
    static void startContainer() {
        POSTGRES_CONTAINER.start();
    }

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();

        pet = new Pet();
        pet.setName("Buddy");
        pet.setStatus(Status.available);

        petRepository.save(pet);
    }

    @Test
    void testSavePet() {
        Pet newPet = new Pet();
        newPet.setName("Max");
        newPet.setStatus(Status.sold);

        Pet savedPet = petService.create(newPet);
        assertEquals(savedPet.getId(), newPet.getId());
        assertEquals(savedPet.getName(), newPet.getName());
        assertEquals(savedPet.getStatus(), newPet.getStatus());
    }

    @Test
    void testFindPetById() throws PetNotFoundException {
        Pet foundPet = petService.findById(pet.getId());
        assertEquals(foundPet.getId(), pet.getId());
        assertEquals(foundPet.getName(), pet.getName());
        assertEquals(foundPet.getStatus(), pet.getStatus());
    }

    @Test
    void testUpdatePet() throws PetNotFoundException {
        Pet updatedPet = petService.updateById(pet.getId(), pet.getName(), Status.sold);
        assertEquals(updatedPet.getId(), pet.getId());
        assertEquals(updatedPet.getName(), pet.getName());
        assertEquals(Status.sold, updatedPet.getStatus());
    }

    @Test
    void testDeletePet() throws PetNotFoundException {
        petService.delete(pet.getId());
        assertThrows(PetNotFoundException.class, () -> petService.findById(pet.getId()));
    }
}
