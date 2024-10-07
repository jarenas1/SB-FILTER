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

    //AUDIT
    @Embedded
    private Audit audit = new Audit();

    //---------------------RELATION----------------------------------------------

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "paletEntity")
    List<LoadEntity> loads = new ArrayList<>();

    //---------------------------GETTERS AND SETTERS-------------------------------------------------

    public @NotNull Long getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull Long capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LoadEntity> getLoads() {
        return loads;
    }

    public void setLoads(List<LoadEntity> loads) {
        this.loads = loads;
    }

    public @NotBlank String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank String location) {
        this.location = location;
    }

    public PaletStatus getPaletStatus() {
        return paletStatus;
    }

    public void setPaletStatus(PaletStatus paletStatus) {
        this.paletStatus = paletStatus;
    }
}
