package org.sibsutis.lab4.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Pet {
    private Long id;
    @NotNull
    private String name;
    private Category category;
    private List<Tag> tags;
    private Status status;
}
