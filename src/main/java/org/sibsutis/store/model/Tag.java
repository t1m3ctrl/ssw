package org.sibsutis.store.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "petstore", name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
