package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.exceptions.RuleMasterException;
import com.perficient.ruleMaster.maper.RuleMapper;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.repository.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    private final TableService tableService;

    public RuleDTO createRule(RuleDTO ruleDTO){

        verifyRuleName(ruleDTO.getRuleName());

        Rule newRule = ruleMapper.fromRuleDTO(ruleDTO);
        newRule.setRuleId(UUID.randomUUID());
        newRule.setRuleTransformed(transformRule(newRule.getRuleDefinition()));

        return ruleMapper.fromRule(ruleRepository.save(newRule));
    }

    private String transformRule(String rule){
        rule=rule.replace("MAYOR QUE ","> ");
        rule=rule.replace("MENOR QUE ","< ");
        rule=rule.replace("IGUAL A ","=== ");
        rule=rule.replace("DIFERENTE A ","!= ");
        rule=rule.replace("VERDADERO ","true ");
        rule=rule.replace("FALSO ","false ");
        rule=rule.replace("Y ","&& ");
        rule=rule.replace("O ","|| ");
        return rule;
    }

    public void verifyRuleName(String ruleName){

        if (ruleRepository.findByName(ruleName).isPresent()){
            throw new RuleMasterException(HttpStatus.CONFLICT, "Rule with name " + ruleName + " already exists");
        }
    }

    //This method converts the column names to values of the record in the rule definition
    public String sendRuleModified(String recordId, String ruleName,String tableName) throws SQLException {

        RuleDTO ruleToEvaluate = getRuleByName(ruleName);

        List<Map<String, Object>> recordObtained = tableService.getRecord(tableName, recordId);

        List<String> columnNames = tableService.getColumnNames(tableName);
        List<String> columnTypes = tableService.getColumnTypes(tableName);

        return modifyRule(recordObtained.get(0), columnNames, columnTypes, ruleToEvaluate.getRuleTransformed());
    }

    public RuleDTO getRuleByName(String ruleName) {
        return ruleMapper.fromRule(ruleRepository.findByName(ruleName)
                .orElseThrow(() -> new RuleMasterException(HttpStatus.NOT_FOUND,"The rule with name "+ruleName+" not exists")));
    }

    public List<RuleDTO> getAllRules(){
        return ruleRepository.findAll().stream().map(ruleMapper::fromRule).toList();
    }

    private String modifyRule(Map<String, Object> recordObtained, List<String> columnNames,
                              List<String> columnTypes, String ruleDefinition){

        for (int i = 0; i < columnNames.size(); i++) {

            if (columnTypes.get(i).equals("varchar")){
                ruleDefinition = ruleDefinition
                        .replace(columnNames.get(i),"'"+recordObtained.get(columnNames.get(i)).toString()+"'");
            }else {
                ruleDefinition = ruleDefinition.replace(columnNames.get(i),recordObtained.get(columnNames.get(i)).toString());
            }
        }
        return ruleDefinition;
    }

}
