package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.RuleDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

public interface RuleAPI {

    String BASE_RULE_URL = "/rules";

    @PostMapping
    RuleDTO createRule(@Valid @RequestBody RuleDTO ruleDTO);

    @GetMapping("/{ruleName}")
    RuleDTO getRule(@PathVariable String ruleName);

    @GetMapping
    List<RuleDTO> getAllRules();

    @GetMapping("/evaluate/{recordId}/{ruleName}")
    String sendRuleModified(@PathVariable String ruleName, @PathVariable String recordId) throws SQLException;
}
