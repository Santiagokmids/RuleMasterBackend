package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.exceptions.RuleMasterException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

public interface RuleAPI {

    String BASE_RULE_URL = "/rules";

    @PostMapping
    RuleDTO createRule(@Valid @RequestBody RuleDTO ruleDTO);

    @GetMapping("/{ruleName}")
    RuleDTO getRule(@PathVariable String ruleName) throws RuleMasterException;

    @GetMapping
    List<RuleDTO> getAllRules();

    @GetMapping("/evaluate/{recordId}/{ruleName}")
    String sendRuleModified(@PathVariable String ruleName, @PathVariable String recordId) throws SQLException;
}
