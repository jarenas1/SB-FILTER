package com.filtro.FILTRO_SPRINGBOOT.model;

import com.filtro.FILTRO_SPRINGBOOT.tools.enums.PaletStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "palets")
public class PaletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String location;

    @NotNull
    @Column(nullable = false)
    private Long capacity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Save the enum like a string
    private PaletStatus paletStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "paletEntity")
    List<LoadEntity> loads = new ArrayList<>();

}
