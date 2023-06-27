package com.perficient.ruleMaster.exceptions;

import org.springframework.http.HttpStatus;

public class RuleMasterException extends RuntimeException{

    private final HttpStatus status;

    public RuleMasterException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }

}
