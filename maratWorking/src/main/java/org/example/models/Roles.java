package org.example.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.models.enums.EnRole;

@Entity
@Getter
@Setter
@Table(name = "roles")
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Roles {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long Id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    EnRole name;

    public Roles(EnRole name) {
        this.name = name;
    }
}
