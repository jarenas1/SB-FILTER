package com.filtro.FILTRO_SPRINGBOOT.service.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.repository.PaletRepository;
import com.filtro.FILTRO_SPRINGBOOT.service.PaletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<PaletEntity> update(PaletEntity paletEntity) {
        Optional<PaletEntity> optionalPaletEntity = paletRepository.findById(paletEntity.getId());

        if (optionalPaletEntity.isPresent()) {
            PaletEntity toUpdatePalet = optionalPaletEntity.get();

            toUpdatePalet.setCapacity(paletEntity.getCapacity());
            toUpdatePalet.setPaletStatus(paletEntity.getPaletStatus());
            toUpdatePalet.setLoads(paletEntity.getLoads());
            toUpdatePalet.setLocation(paletEntity.getLocation());

            return Optional.of(paletRepository.save(toUpdatePalet));
        }
        return optionalPaletEntity;
    }

    @Override
    public Optional<PaletEntity> delete(String id) {

        PaletEntity paletEntity = paletRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Palet not found with ID: " + id));

        paletRepository.delete(paletEntity);

        return Optional.of(paletEntity); // Devuelve la entidad eliminada
    }
}
