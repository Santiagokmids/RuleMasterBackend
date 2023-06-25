package com.perficient.ruleMaster.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
