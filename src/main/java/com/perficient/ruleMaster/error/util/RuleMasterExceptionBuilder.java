package com.perficient.ruleMaster.error.util;

import com.perficient.ruleMaster.error.exception.DetailBuilder;
import com.perficient.ruleMaster.error.exception.RuleMasterError;
import com.perficient.ruleMaster.error.exception.RuleMasterErrorDetail;
import com.perficient.ruleMaster.error.exception.RuleMasterException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Supplier;

public class RuleMasterExceptionBuilder {

    public static Supplier<RuntimeException> createRuleMasterException(String message, HttpStatus httpStatus, DetailBuilder... details) {
        return () -> new RuleMasterException(createRuleError(message, httpStatus, details), message);
    }

    public static RuleMasterError createRuleError(String message, HttpStatus httpStatus, DetailBuilder... details){
        return RuleMasterError.builder().status(httpStatus)
                .details(
                        Arrays.stream(details)
                                .map(RuleMasterExceptionBuilder::mapToRuleErrorDetail)
                                .toList()
                ).build();
    }

    public static RuleMasterErrorDetail mapToRuleErrorDetail(DetailBuilder detailBuilder) {
        return RuleMasterErrorDetail.builder()
                .errorCode(detailBuilder.getErrorCode().getCode())
                .errorMessage(detailBuilder.getErrorCode().getMessage().formatted(detailBuilder.getFields()))
                .build();

    }
}
