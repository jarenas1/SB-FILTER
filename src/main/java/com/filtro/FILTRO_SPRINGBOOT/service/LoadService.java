package com.filtro.FILTRO_SPRINGBOOT.service;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface LoadService {
    List<LoadEntity> findAll();

    Optional<LoadEntity> findById();

    LoadEntity save (LoadEntity loadEntity);

    Optional<LoadEntity> findByUserEntity(UserEntity userEntity);

    Optional<LoadEntity> findByPaletEntity(UserEntity userEntity);
}

