package com.filtro.FILTRO_SPRINGBOOT.service.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.repository.PaletRepository;
import com.filtro.FILTRO_SPRINGBOOT.service.PaletService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PaletImplementation implements PaletService {

    @Autowired
    private PaletRepository paletRepository;

    @Override
    public List<PaletEntity> findAll() {
        return paletRepository.findAll();
    }

    @Override
    public Optional<PaletEntity> findById(String id) {
        return paletRepository.findById(id);
    }

    @Override
    public PaletEntity save(PaletEntity paletEntity) {
        return paletRepository.save(paletEntity);
    }

    @Override
    public PaletEntity update(PaletEntity paletEntity) {
        Optional<PaletEntity> optionalPaletEntity = paletRepository.findById(paletEntity.getId());
        if (optionalPaletEntity.isPresent()){
            PaletEntity toUpdatePalet = optionalPaletEntity.get();

            toUpdatePalet.setCapacity(paletEntity.getCapacity());
            toUpdatePalet.setPaletStatus(paletEntity.getPaletStatus());
            toUpdatePalet.setLoads(paletEntity.getLoads());
            toUpdatePalet.setLocation(paletEntity.getLocation());

            return paletRepository.save(toUpdatePalet);
        }
        return null ;
    }

    @Override
    public Optional<PaletEntity> delete(String id) {

        Optional<PaletEntity> optionalPaletEntity = paletRepository.findById(id);

        optionalPaletEntity.ifPresent(paletEntity -> {
            paletRepository.delete(paletEntity);
        });
        return optionalPaletEntity;
    }
}
