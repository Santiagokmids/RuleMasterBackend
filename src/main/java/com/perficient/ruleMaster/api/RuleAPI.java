package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.model.Rule;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

public interface RuleAPI {

    String BASE_RULE_URL = "/rules";

    @PostMapping("/createRule")
    RuleDTO createRule(@RequestBody RuleDTO ruleDTO);

    @GetMapping("/{recordId}")
    Rule getRule(@PathVariable String ruleName);

    @GetMapping("/evaluate/{recordId}/{ruleName}")
    String sendRuleModified(@PathVariable String ruleName, @PathVariable String recordId) throws SQLException;
}
