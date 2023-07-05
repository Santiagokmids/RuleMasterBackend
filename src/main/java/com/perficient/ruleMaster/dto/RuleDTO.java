package com.perficient.ruleMaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleDTO {

    @NotBlank(message = "Falta nombre de la regla.")
    private String ruleName;

    private String ruleDefinition;

    private  String ruleTransformed;
}
