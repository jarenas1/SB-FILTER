package com.filtro.FILTRO_SPRINGBOOT.controller.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.service.PaletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pallets")
public class PaletsController {

    @Autowired
    private PaletService paletService;

    @GetMapping
    public List<PaletEntity> list(){
        return paletService.findAll();
    }

    @GetMapping("/{id}") //recibe el id por el navegador y retorna una respuesta buena o mala
    public ResponseEntity<?> view (@PathVariable String id){
        Optional<PaletEntity> optionalProduct = paletService.findById(id);
        if (optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PaletEntity paletEntity, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        PaletEntity palet = paletService.save(paletEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(palet);
    }

    @PutMapping
    public ResponseEntity<?> update (@Valid @RequestBody PaletEntity paletEntity, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<PaletEntity> paletEntityOptional = Optional.ofNullable(paletService.update(paletEntity));
        if (paletEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(paletEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public PaletEntity delete (@PathVariable  String id){
        return paletService.delete(id).get();
    }


    //VALIDATION
    private ResponseEntity<?> validation(BindingResult result) {
        //CREACION DEL ERROR
        Map<String, String> errors = new HashMap<>();
        //Iteramos errores para añadirlos al map
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), "The field "+fieldError.getField()+" "+ fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
