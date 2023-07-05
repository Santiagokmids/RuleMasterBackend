package com.perficient.ruleMaster.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    @Id
    private UUID ruleId;

    private String ruleName;

    private String ruleDefinition;

    private String ruleTransformed;
}
