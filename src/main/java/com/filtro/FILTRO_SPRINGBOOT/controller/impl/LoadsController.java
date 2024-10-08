package com.filtro.FILTRO_SPRINGBOOT.controller.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.PaletEntity;
import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.service.LoadService;
import com.filtro.FILTRO_SPRINGBOOT.service.PaletService;
import com.filtro.FILTRO_SPRINGBOOT.service.UserService;
import com.filtro.FILTRO_SPRINGBOOT.service.impl.MailSenderImplementation;
import com.filtro.FILTRO_SPRINGBOOT.tools.enums.LoadStatus;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/loads")
public class LoadsController {

    @Autowired
    private LoadService loadService;

    @Autowired
    private PaletService paletService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderImplementation mailSenderImplementation;

    @GetMapping
    public List<LoadEntity> finAll(){
        return loadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view (@PathVariable String id){
        Optional<LoadEntity> optionalLoad = loadService.findById(id);
        if (optionalLoad.isPresent()){
            return ResponseEntity.ok(optionalLoad.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public  ResponseEntity<?> create (@Valid @RequestBody LoadEntity loadEntity, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        LoadEntity load = loadService.save(loadEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(load);
    }

    @PutMapping
    public ResponseEntity<?> update (@Valid @RequestBody  LoadEntity loadEntity, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<LoadEntity> loadEntityOptional = loadService.update(loadEntity);
        if (loadEntityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(loadEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateLoadStatus(@PathVariable String id, @RequestBody Map<String, String> update) {
        String statusValue = update.get("loadStatus"); // get the enum value in each request

        try {
            LoadStatus newStatus = LoadStatus.valueOf(statusValue); // String to enum
            LoadEntity updatedLoad = loadService.patchStatus(id, newStatus); // update
            return ResponseEntity.ok(updatedLoad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid LoadStatus value");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/damage")
    public ResponseEntity<?> updateActiveStatus(@PathVariable String id, @RequestBody Map<String, Boolean> update) {
        Boolean newStatus = update.get("damage"); // get the new value from de map

        if (newStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or missing 'damage' value");
        }
        //traemos los admins pa enviar el correo
        List<UserEntity> users = userService.findAll();

        try {
            LoadEntity updatedLoad = loadService.patchDamage(id, newStatus);
            //SEND EMAIL TO ADMINS
            for(UserEntity user: users){
                if (user.isAdmin()){
                    mailSenderImplementation.email(user.getEmail(),user.getUsername(),"SE HA REGISTRADO UN NUEVO DAÑO EN UNA CARGA");
                }
            }
            return ResponseEntity.ok(updatedLoad);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/pallet/{id}")
    public ResponseEntity<?> findByPalet(@PathVariable String id){
        Optional<PaletEntity> optionalPalet = paletService.findById(id);
        if (optionalPalet.isPresent()){
            return ResponseEntity.ok(loadService.findByPaletEntity(optionalPalet.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/carriers/{id}")
    public ResponseEntity<?> findByCreer(@PathVariable String id){
        Optional<UserEntity> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(loadService.findByUserEntity(optionalUser.get()));
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>(); //creating the map that contains the error
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+ fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
