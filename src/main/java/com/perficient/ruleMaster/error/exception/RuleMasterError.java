package com.perficient.ruleMaster.error.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
public class RuleMasterError {

    private HttpStatus status;
    private List<RuleMasterErrorDetail> details;
}
