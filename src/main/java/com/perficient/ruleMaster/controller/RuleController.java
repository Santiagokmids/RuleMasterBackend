package com.perficient.ruleMaster.controller;

import com.perficient.ruleMaster.api.RuleAPI;
import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.service.RuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import static com.perficient.ruleMaster.api.RuleAPI.BASE_RULE_URL;

@RestController
@RequestMapping(BASE_RULE_URL)
@AllArgsConstructor
public class RuleController implements RuleAPI {

    private final RuleService ruleService;

    @Override
    public RuleDTO createRule(RuleDTO ruleDTO) {
        return ruleService.createRule(ruleDTO);
    }

    @Override
    public Rule getRule(String ruleName) {
        return ruleService.getRuleByName(ruleName);
    }

    @Override
    public List<RuleDTO> getAllRules() {
        return ruleService.getAllRules();
    }

    @Override
    public String sendRuleModified(String ruleName, String recordId) throws SQLException {
        return ruleService.sendRuleModified(recordId, ruleName,"records");
    }
}
