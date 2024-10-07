package com.filtro.FILTRO_SPRINGBOOT.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
public class Audit {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    //---------------------------GETTERS AND SETTERS-------------------------------------------------

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //IMPLEMENTING THE CICLES OF LIVE TO CRETE THE AUDIT

    @PrePersist
    public void prePersist(){
        System.out.println("prePersist");
        this.createdAt = LocalDateTime.now(); //Asign the local date to the atribute
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("preUpdate");
        this.updatedAt = LocalDateTime.now();

    }
}
