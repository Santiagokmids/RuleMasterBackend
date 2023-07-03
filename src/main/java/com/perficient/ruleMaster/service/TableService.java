package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.ColumnAdditionDTO;
import com.perficient.ruleMaster.dto.RecordAdditionDTO;
import com.perficient.ruleMaster.error.exception.DetailBuilder;
import com.perficient.ruleMaster.error.exception.ErrorCode;
import com.perficient.ruleMaster.model.TableData;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.perficient.ruleMaster.error.util.RuleMasterExceptionBuilder.createRuleMasterException;

@Service
@AllArgsConstructor
public class TableService {
    private final JdbcTemplate jdbcTemplate;

    public ColumnAdditionDTO addColumnToTable(ColumnAdditionDTO columnAdditionDTO) throws SQLException {
        verifyColumnName(columnAdditionDTO.getColumnName(),columnAdditionDTO.getTableName());
        String sql = "ALTER TABLE " + columnAdditionDTO.getTableName()  +
                    " ADD COLUMN " + columnAdditionDTO.getColumnName() +
                    " " + columnAdditionDTO.getColumnType();
        jdbcTemplate.execute(sql);

        return columnAdditionDTO;
    }


    public void verifyColumnName(String columnName, String tableName) throws SQLException {
        TableData tableData= getTableData(tableName);

        if (tableData.getColumnNames().contains(columnName)){
            throw createRuleMasterException(
                    "Duplicated column name",
                    HttpStatus.CONFLICT,
                    new DetailBuilder(ErrorCode.ERR_DUPLICATED, "Column with name", columnName)
            ).get();
        }
    }


    public TableData getTableData(String tableName) throws SQLException {
        DataSource dataSource = jdbcTemplate.getDataSource();
        Connection connection = DataSourceUtils.getConnection(dataSource);

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

        // Retrieve column names and types
        List<String> columnNames = new ArrayList<String>();
        List<String> columnTypes = new ArrayList<String>();

        try {
            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME"));
                columnTypes.add(resultSet.getString("TYPE_NAME"));
            }
        } finally {
            resultSet.close(); // Close the ResultSet
            DataSourceUtils.releaseConnection(connection, dataSource); // Release the Connection
        }

        // Retrieve table records
        List<Map<String, Object>> records = jdbcTemplate.queryForList("SELECT * FROM " + tableName);

        return new TableData(tableName, columnNames, columnTypes, records);
    }

    public RecordAdditionDTO addRecord(RecordAdditionDTO recordAdditionDTO) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(recordAdditionDTO.getTableName()).append(" (");

        // Append column names
        Map<String, Object> record =recordAdditionDTO.getRecord();
        sql.append(record.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));


        sql.append(") VALUES (");

        // Append placeholders for values
        String[] valuePlaceholders = new String[record.size()];
        for (int i = 0; i < record.size(); i++) {
            valuePlaceholders[i] = "?";
        }
        sql.append(String.join(", ", valuePlaceholders));

        sql.append(")");

        record.put("record_id", UUID.randomUUID());

        // Execute the SQL statement with the record values
        jdbcTemplate.update(sql.toString(), record.values().toArray());

        return  recordAdditionDTO;
    }

    public List<Map<String, Object>> getRecord(String tableName, String recordId){

        List<Map<String, Object>> records = jdbcTemplate.queryForList("SELECT * FROM " + tableName);

        List<Map<String, Object>> recordObtained = records.stream()
                .filter(record -> record.get("record_id").toString().equals(recordId)).toList();
        System.out.println("Cual es el bucle");
        if (recordObtained.isEmpty()){
            throw createRuleMasterException(
                    "Record not found",
                    HttpStatus.NOT_FOUND,
                    new DetailBuilder(ErrorCode.ERR_404, "Record with id", recordId)
            ).get();
        }

        return recordObtained;
    }



}
