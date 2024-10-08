package com.filtro.FILTRO_SPRINGBOOT.service;

import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;

import java.util.List;
import java.util.Optional;

public interface PaletService {

    List<PaletEntity> findAll();

    Optional<PaletEntity> findById(String id);

    PaletEntity save(PaletEntity paletEntity);

    Optional<PaletEntity> update(PaletEntity paletEntity);

    Optional<PaletEntity> delete(String id);






}
