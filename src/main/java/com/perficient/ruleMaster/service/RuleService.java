package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.error.exception.DetailBuilder;
import com.perficient.ruleMaster.error.exception.ErrorCode;
import com.perficient.ruleMaster.error.exception.RuleMasterError;
import com.perficient.ruleMaster.error.exception.RuleMasterException;
import com.perficient.ruleMaster.maper.RuleMapper;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.model.TableData;
import com.perficient.ruleMaster.repository.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.perficient.ruleMaster.error.util.RuleMasterExceptionBuilder.createRuleMasterException;

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
            throw createRuleMasterException(
                    "Duplicated rule name",
                    HttpStatus.CONFLICT,
                    new DetailBuilder(ErrorCode.ERR_DUPLICATED, "Rule with name", ruleName)
            ).get();
        }
    }

    //This method converts the column names to values of the record in the rule definition
    public String sendRuleModified(String recordId, String ruleName,String tableName) throws SQLException {

        RuleDTO ruleToEvaluate = getRuleByName(ruleName);

        List<Map<String, Object>> recordObtained = tableService.getRecord(tableName, recordId);

        TableData tableData=tableService.getTableData(tableName);
        List<String> columnNames = tableData.getColumnNames();
        List<String> columnTypes = tableData.getColumnTypes();

        return modifyRule(recordObtained.get(0), columnNames, columnTypes, ruleToEvaluate.getRuleTransformed());
    }

    public RuleDTO getRuleByName(String ruleName) {

        if (ruleRepository.findByName(ruleName).isEmpty()){
            throw createRuleMasterException(
                    "Rule not found",
                    HttpStatus.NOT_FOUND,
                    new DetailBuilder(ErrorCode.ERR_404, "Rule with name", ruleName)
            ).get();
        }
        return ruleMapper.fromRule(ruleRepository.findByName(ruleName).get());
    }

    public List<RuleDTO> getAllRules(){
        return ruleRepository.findAll().stream().map(ruleMapper::fromRule).toList();
    }

    private String modifyRule(Map<String, Object> recordObtained, List<String> columnNames,
                              List<String> columnTypes, String ruleDefinition){

        for (int i = 0; i < columnNames.size(); i++) {

            if (isRecordObtainedNotNull(recordObtained, columnNames, i)){
                ruleDefinition = putValues(recordObtained, columnNames, columnTypes, ruleDefinition, i);

            }else{
                ruleDefinition = ruleDefinition.replace(columnNames.get(i), "null");
            }
        }
        return ruleDefinition;
    }

    private boolean isRecordObtainedNotNull(Map<String, Object> recordObtained, List<String> columnNames, int counter){
        return recordObtained.get(columnNames.get(counter)) != null;
    }

    private String putValues(Map<String, Object> recordObtained, List<String> columnNames,
                             List<String> columnTypes, String ruleDefinition, int counter){

        if (columnTypes.get(counter).equals("varchar")){
            ruleDefinition = ruleDefinition
                    .replace(columnNames.get(counter),"'"+recordObtained.get(columnNames.get(counter)).toString()+"'");
        }else {
            ruleDefinition = ruleDefinition.replace(columnNames.get(counter),recordObtained.get(columnNames.get(counter)).toString());
        }

        return ruleDefinition;
    }
}
