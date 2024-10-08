package com.filtro.FILTRO_SPRINGBOOT.service;

import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity save (UserEntity userEntity);

    Optional<UserEntity> findById(String id);

    List<UserEntity> findAll();
}
