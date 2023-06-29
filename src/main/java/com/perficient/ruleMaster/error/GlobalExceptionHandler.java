package com.perficient.ruleMaster.error;

import com.perficient.ruleMaster.error.exception.RuleMasterError;
import com.perficient.ruleMaster.error.exception.RuleMasterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RuleMasterError> handleRuleMasterException(RuleMasterException ruleException){
        return ResponseEntity.status(ruleException.getRuleMasterError().getStatus()).body(ruleException.getRuleMasterError());
    }
}
