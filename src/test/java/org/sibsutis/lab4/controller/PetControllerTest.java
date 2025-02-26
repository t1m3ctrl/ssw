package org.sibsutis.lab4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sibsutis.lab4.model.Pet;
import org.sibsutis.lab4.model.Status;
import org.sibsutis.lab4.service.PetService;
import org.sibsutis.lab4.exceptions.PetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PetService petService;

//    @InjectMocks
//    private PetController petController;

    private Pet pet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
    }

    @Test
    public void testGetPet() throws Exception {
        when(petService.findById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.status").value("available"));
    }

//    @Test
//    public void testUpdatePet() throws Exception {
//        when(petService.update(any(Pet.class))).thenReturn(pet);
//
//        mockMvc.perform(put("/api/v3/pet")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"id\": 1, \"name\": \"Buddy\", \"status\": \"available\" }"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Buddy"))
//                .andExpect(jsonPath("$.status").value("available"));
//    }
//
//    @Test
//    public void testDeletePet() throws Exception {
//        when(petService.delete(1L)).thenReturn(pet);
//
//        mockMvc.perform(delete("/api/v3/pet/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Buddy"));
//    }
//
//    @Test
//    public void testUpdatePetById() throws Exception {
//        when(petService.updateById(1L, "New Name", Status.sold)).thenReturn(pet);
//
//        mockMvc.perform(post("/api/v3/pet/1")
//                        .param("name", "New Name")
//                        .param("status", "sold"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Buddy"))
//                .andExpect(jsonPath("$.status").value("available"));
//    }

    @Test
    public void testPetNotFoundException() throws Exception {
        when(petService.findById(999L)).thenThrow(new PetNotFoundException("Pet not found"));

        mockMvc.perform(get("/api/v3/pet/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found"));
    }

    @Test
    public void testIllegalArgumentException() throws Exception {
        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"invalid_id\", \"name\": \"Buddy\" }"))
                .andExpect(status().isBadRequest());
    }
}
