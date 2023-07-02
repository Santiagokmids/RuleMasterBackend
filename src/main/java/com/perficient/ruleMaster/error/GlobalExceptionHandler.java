package com.perficient.ruleMaster.error;

import com.perficient.ruleMaster.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RuleMasterError> handleRuleMasterException(RuleMasterException ruleException){
        return ResponseEntity.status(ruleException.getRuleMasterError().getStatus()).body(ruleException.getRuleMasterError());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RuleMasterError> handleValidationExceptions( MethodArgumentNotValidException ex) {
        var errorBuilder = RuleMasterError.builder().status(HttpStatus.BAD_REQUEST);
        var details = ex.getBindingResult().getAllErrors().stream().map(this::mapBindingResultToError).toList();
        var error = errorBuilder.details(details).build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    private RuleMasterErrorDetail mapBindingResultToError(ObjectError objectError){
        var message = ErrorCode.ERR_400.getMessage().formatted("",objectError.getDefaultMessage());
        return RuleMasterErrorDetail.builder()
                .errorCode(ErrorCode.ERR_400.getCode())
                .errorMessage(message)
                .build();

    }

    /*
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RuleMasterError> handleRuntimeException(RuntimeException runtimeException){
        var error = createRuleError(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, new DetailBuilder(ErrorCode.ERR_500));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    */


}
