package com.filtro.FILTRO_SPRINGBOOT.controller.handlerException;

import com.filtro.FILTRO_SPRINGBOOT.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Error> wightOfProducts(Exception ex) {

        //CREAMOS EL ERROR QUE DEVOLVEREMOS
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error añadiendo más peso al palet");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.internalServerError().body(error);

    }



    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFound(Exception ex) {

        Error e = new Error();
        e.setStatus(404);
        e.setMessage(ex.getLocalizedMessage());
        e.setDate(new Date());
        e.setError("error, rcurso no encontrado");

        return ResponseEntity.status(404).body(e);
    }


    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Error> numberFormatEx (Exception ex){
        Error e = new Error();
        e.setDate(new Date());
        e.setError("Numero no se puede formatear");
        e.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        e.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.internalServerError().body(e);
    }

    //CREAMOS UN HANDLER PARA CUANDO EL USUARIO ES NULL Y CUANDO EL ROLE ES NULL
    @ExceptionHandler({NullPointerException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Error> UserPointer (Exception ex) {
        //CREAMOS EL ERROR QUE DEVOLVEREMOS
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error, Usuario o rol no existen");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.internalServerError().body(error);
    }
}
