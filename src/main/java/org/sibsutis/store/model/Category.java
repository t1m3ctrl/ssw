package org.sibsutis.store.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "petstore", name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
