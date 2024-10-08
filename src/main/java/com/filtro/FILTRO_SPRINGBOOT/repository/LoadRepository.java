package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoadRepository extends JpaRepository<LoadEntity, String> {

    public List<LoadEntity> findByUserEntity(UserEntity userEntity);
    public List<LoadEntity> findByPaletEntity(PaletEntity paletEntity);
}
