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

    private Boolean damage = false;

    //relation-----
    @ManyToOne
    @JoinColumn(name = "id_palet")
    private PaletEntity paletEntity;

    //relation-----
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;

    //AUDIT
    @Embedded
    private Audit audit = new Audit();

    //---------------------------GETTERS AND SETTERS-------------------------------------------------

    public @NotNull Long getDimensions() {
        return dimensions;
    }

    public void setDimensions(@NotNull Long dimensions) {
        this.dimensions = dimensions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public PaletEntity getPaletEntity() {
        return paletEntity;
    }

    public void setPaletEntity(PaletEntity paletEntity) {
        this.paletEntity = paletEntity;
    }

    public @NotNull Long getWeight() {
        return weight;
    }

    public void setWeight(@NotNull Long weight) {
        this.weight = weight;
    }
}
