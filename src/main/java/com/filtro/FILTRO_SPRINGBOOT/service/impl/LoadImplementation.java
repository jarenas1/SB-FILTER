package com.filtro.FILTRO_SPRINGBOOT.service.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.repository.LoadRepository;
import com.filtro.FILTRO_SPRINGBOOT.repository.PaletRepository;
import com.filtro.FILTRO_SPRINGBOOT.service.LoadService;
import com.filtro.FILTRO_SPRINGBOOT.tools.enums.LoadStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LoadImplementation implements LoadService {

    @Autowired
    private LoadRepository loadRepository;

    @Override
    public List<LoadEntity> findAll() {
        return loadRepository.findAll();
    }

    @Override
    public Optional<LoadEntity> findById(String id) {
        return loadRepository.findById(id);
    }

    @Override
    public LoadEntity save(LoadEntity loadEntity) {
        return loadRepository.save(loadEntity);
    }

    @Override
    public LoadEntity update(LoadEntity loadEntity) {
        Optional<LoadEntity> optionalLoadEntity = loadRepository.findById(loadEntity.getId());

        if (optionalLoadEntity.isPresent()) {
            LoadEntity toUpdateLoad = optionalLoadEntity.get();


          toUpdateLoad.setLoadStatus(loadEntity.getLoadStatus());
          toUpdateLoad.setDimensions(loadEntity.getDimensions());
          toUpdateLoad.setWeight(loadEntity.getDimensions());
          toUpdateLoad.setPaletEntity(loadEntity.getPaletEntity());
          toUpdateLoad.setDamage(loadEntity.getDamage());
          toUpdateLoad.setUserEntity(loadEntity.getUserEntity());


            return loadRepository.save(toUpdateLoad);
        } else {
            throw new EntityNotFoundException("Load not found with ID: " + loadEntity.getId());
        }
    }

    @Override
    public LoadEntity patchDamage(String id) {
        LoadEntity loadEntity = loadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("load not found with ID: " + id));
        //patch
        loadEntity.setDamage(!loadEntity.getDamage()); //if true -> false , if false -> true
        loadRepository.save(loadEntity);
        return loadEntity;
    }

    @Override
    public LoadEntity patchStatus(String id, LoadStatus loadStatus) {
        LoadEntity loadEntity = loadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("load not found with ID: " + id));

        loadEntity.setLoadStatus(loadStatus);
        return loadRepository.save(loadEntity);
    }

    @Override
    public List<LoadEntity> findByUserEntity(UserEntity userEntity) {
        return loadRepository.findByUserEntity(userEntity);
    }

    @Override
    public List<LoadEntity> findByPaletEntity(PaletEntity paletEntity) {
        return loadRepository.findByPaletEntity(paletEntity);
    }
}
