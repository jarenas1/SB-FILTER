package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaletRepository extends JpaRepository<PaletEntity, String> {
}
