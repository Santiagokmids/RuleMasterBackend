package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.maper.RuleMapper;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.repository.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    private final JdbcTemplate jdbcTemplate;

    private final TableService tableService;

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

    //This method converts the column names to values of the record in the rule definition
    public String sendRuleModified(String recordId, String ruleName,String tableName) throws SQLException {

        Rule ruleToEvaluate = getRuleByName(ruleName);

        List<Map<String, Object>> recordObtained = tableService.getRecord(tableName, recordId);

        List<String> columnNames = tableService.getColumns(tableName);

        return modifyRule(recordObtained.get(0), columnNames, ruleToEvaluate.getRuleDefinition());
    }

    public Rule getRuleByName(String ruleName){
        return ruleRepository.findByName(ruleName)
                .orElseThrow(() -> new RuntimeException("The rule with name "+ruleName+" not exists"));
    }

    private String modifyRule(Map<String, Object> recordObtained, List<String> columnNames, String ruleDefinition){

        for (String columnName : columnNames) {
            ruleDefinition = ruleDefinition.replace(columnName, recordObtained.get(columnName).toString());
        }

        return ruleDefinition;
    }

}
