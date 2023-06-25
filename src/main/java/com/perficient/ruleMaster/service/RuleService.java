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

    //This method converts the column names to values of the record
    public String sendRuleModified(String recordId, String ruleName, String tableName) throws SQLException {

        Rule ruleToEvaluate = getRuleByName(ruleName);

        //Toca validar que el recordId exista o limitarlo en el front

        //Esto de aquí se puede poner en el service de TableData para obtener un registro específico
            List<Map<String, Object>> records = jdbcTemplate.queryForList("SELECT * FROM " + tableName);

            List<Map<String, Object>> recordObtained = records.stream()
                    .filter(record -> record.get("record_id").toString().equals(recordId)).toList();

        //Esto también se puede poner en el service de TableData para obtener las columnas
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Retrieve column names and types
            List<String> columnNames = new ArrayList<String>();

            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME"));
            }

        return modifyRule(recordObtained.get(0), columnNames, ruleToEvaluate.getRuleDefinition());
    }

    private Rule getRuleByName(String ruleName){
        return ruleRepository.findByName(ruleName).orElseThrow(() -> new RuntimeException("The rule "+ruleName+" not exists"));
    }

    private String modifyRule(Map<String, Object> recordObtained, List<String> columnNames, String ruleDefinition){

        for (String columnName : columnNames) {
            ruleDefinition = ruleDefinition.replace(columnName, recordObtained.get(columnName).toString());
        }

        return ruleDefinition;
    }

}
