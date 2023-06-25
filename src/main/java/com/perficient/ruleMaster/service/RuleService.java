package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.maper.RuleMapper;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.repository.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    public RuleDTO createRule(RuleDTO ruleDTO){

        verifyRuleName(ruleDTO.getRuleName());

        Rule newRule = ruleMapper.fromRuleDTO(ruleDTO);
        newRule.setRuleId(UUID.randomUUID());

        return ruleMapper.fromRule(ruleRepository.save(newRule));
    }

    public void verifyRuleName(String ruleName){

        if (ruleRepository.findByName(ruleName).isPresent()){
            throw new RuntimeException("A rule with this name "+ruleName+" already exists");
        }
    }

    public RuleDTO sendRuleToEvaluate(String recordId, String ruleName){

        Rule ruleToEvaluate = getRuleByName(ruleName);

        //Toca validar que el recordId exista o limitarlo en el front

        String[] expressions = ruleToEvaluate.getRuleDefinition().split("[()]");

        prepareRule(expressions);

        return null;
    }

    private Rule getRuleByName(String ruleName){
        return ruleRepository.findByName(ruleName).orElseThrow(() -> new RuntimeException("The rule "+ruleName+" not exists"));
    }

    //This method converts the expressions to boolean values to evaluate the rule in the frontEnd
    private String prepareRule(String[] expressions){
        return "";
    }
}
