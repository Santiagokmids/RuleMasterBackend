package com.perficient.ruleMaster.error.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleMasterErrorDetail {

    private String errorCode;

    private String errorMessage;
}
