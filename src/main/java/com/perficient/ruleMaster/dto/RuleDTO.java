package com.perficient.ruleMaster.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleDTO {

    @NotBlank(message = "faltante")
    private String ruleName;

    private String ruleDefinition;

    private  String ruleTransformed;
}
