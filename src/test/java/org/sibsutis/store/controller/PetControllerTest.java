package org.sibsutis.store.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sibsutis.store.model.Pet;
import org.sibsutis.store.model.Status;
import org.sibsutis.store.service.PetService;
import org.sibsutis.store.exceptions.PetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("store")
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    public PetService petService;
    private Pet pet;

    @BeforeEach
    public void setup() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
        pet.setStatus(Status.available);
    }

    @Test
    public void testCreatePet() throws Exception {
        when(petService.create(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"name\": \"Buddy\", \"status\": \"available\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.status").value("available"));

        Mockito.verify(petService, Mockito.times(1)).create(any(Pet.class));
    }

    @Test
    public void testGetPet() throws Exception {
        when(petService.findById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.status").value("available"));

        Mockito.verify(petService, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdatePet() throws Exception {
        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("New Name");
        updatedPet.setStatus(Status.pending);

        when(petService.update(any(Pet.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"name\": \"New Name\", \"status\": \"pending\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedPet.getName()))
                .andExpect(jsonPath("$.status").value(updatedPet.getStatus().name()));

        Mockito.verify(petService, Mockito.times(1)).update(any(Pet.class));
    }

    @Test
    public void testDeletePet() throws Exception {
        when(petService.delete(1L)).thenReturn(pet);

        mockMvc.perform(delete("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));

        Mockito.verify(petService, Mockito.times(1)).delete(1L);
    }

    @Test
    public void testUpdatePetById() throws Exception {
        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("New Name");
        updatedPet.setStatus(Status.pending);

        when(petService.updateById(1L, "New Name", Status.pending)).thenReturn(updatedPet);

        mockMvc.perform(post("/api/v3/pet/1")
                        .param("name", "New Name")
                        .param("status", "pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedPet.getName()))
                .andExpect(jsonPath("$.status").value(updatedPet.getStatus().name()));

        Mockito.verify(petService, Mockito.times(1)).updateById(1L, "New Name", Status.pending);
    }

    @Test
    public void testPetNotFoundException() throws Exception {
        when(petService.findById(999L)).thenThrow(new PetNotFoundException("Pet not found"));

        mockMvc.perform(get("/api/v3/pet/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found"));

        Mockito.verify(petService, Mockito.times(1)).findById(999L);
    }

    @Test
    public void testIllegalArgumentException() throws Exception {
        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"invalid_id\", \"name\": \"Buddy\" }"))
                .andExpect(status().isBadRequest());

        Mockito.verify(petService, Mockito.never()).create(any(Pet.class));
    }
}
