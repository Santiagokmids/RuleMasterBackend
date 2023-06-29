package com.perficient.ruleMaster.error.exception;

import lombok.Getter;

@Getter
public class RuleMasterException extends RuntimeException{

    private final RuleMasterError ruleMasterError;

    public RuleMasterException(RuleMasterError ruleMasterError, String message){
        super(message);
        this.ruleMasterError = ruleMasterError;
    }
}
