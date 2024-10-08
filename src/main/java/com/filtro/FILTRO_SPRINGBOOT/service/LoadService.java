package com.filtro.FILTRO_SPRINGBOOT.service;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.tools.enums.LoadStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoadService {
    List<LoadEntity> findAll();

    Optional<LoadEntity> findById(String id);

    LoadEntity save (LoadEntity loadEntity);

    Optional<LoadEntity> update (LoadEntity loadEntity);

    LoadEntity patchDamage(String id, boolean status);

    LoadEntity patchStatus(String id, LoadStatus newStatus);

    List<LoadEntity> findByUserEntity(UserEntity userEntity);

    List<LoadEntity> findByPaletEntity(PaletEntity paletEntity);
}

