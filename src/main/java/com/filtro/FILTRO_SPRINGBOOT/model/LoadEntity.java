package com.filtro.FILTRO_SPRINGBOOT.model;

import com.filtro.FILTRO_SPRINGBOOT.tools.enums.LoadStatus;
import com.filtro.FILTRO_SPRINGBOOT.tools.enums.PaletStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "loads")
public class LoadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private Long weight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Save the enum like a string
    private LoadStatus loadStatus;

    @NotNull
    private Long dimensions;
    @ManyToOne
    @JoinColumn(name = "id_palet")
    private PaletEntity paletEntity;
}
