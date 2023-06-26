package com.perficient.ruleMaster.service;

import com.perficient.ruleMaster.dto.ColumnAdditionDTO;
import com.perficient.ruleMaster.dto.RecordAdditionDTO;
import com.perficient.ruleMaster.model.TableData;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TableService {
    private final JdbcTemplate jdbcTemplate;

    public ColumnAdditionDTO addColumnToTable(ColumnAdditionDTO columnAdditionDTO) {
        String sql = "ALTER TABLE " + columnAdditionDTO.getTableName()  +
                    " ADD COLUMN " + columnAdditionDTO.getColumnName() +
                    " " + columnAdditionDTO.getColumnType();
        jdbcTemplate.execute(sql);

        return columnAdditionDTO;
    }

    public TableData getTableData(String tableName) throws SQLException {
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        // Retrieve column names and types
        List<String> columnNames = new ArrayList<String>();
        List<String> columnTypes = new ArrayList<String>();

        while (resultSet.next()) {
            columnNames.add(resultSet.getString("COLUMN_NAME"));
            columnTypes.add(resultSet.getString("TYPE_NAME"));
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

}
