package com.filtro.FILTRO_SPRINGBOOT.controller.impl;

import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import com.filtro.FILTRO_SPRINGBOOT.service.UserService;
import com.filtro.FILTRO_SPRINGBOOT.service.impl.UserImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(originPatterns = "*") //ALLOWING ROUTES
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity userEntity, BindingResult result){
        //validations
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userEntity));
    }

    //CRETE USER WITH ADMIN FORBIDEN
    @PostMapping("/register")
    public ResponseEntity<?> registrer(@Valid  @RequestBody UserEntity userEntity, BindingResult result){
        //validations
        if(result.hasFieldErrors()){
            return validation(result);
        }
        //set admin in false
        userEntity.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userEntity));
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
