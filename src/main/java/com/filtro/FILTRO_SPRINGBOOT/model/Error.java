package com.filtro.FILTRO_SPRINGBOOT.model;

import java.util.Date;

//Basic structure to create a customized error
public class Error {
    private String message;
    private String error;
    private int status;
    private Date date;

    //---------------------------GETTERS AND SETTERS-------------------------------------------------

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
