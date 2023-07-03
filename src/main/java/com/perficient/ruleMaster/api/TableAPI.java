package com.perficient.ruleMaster.api;

import com.perficient.ruleMaster.dto.ColumnAdditionDTO;
import com.perficient.ruleMaster.dto.RecordAdditionDTO;
import com.perficient.ruleMaster.model.TableData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

public interface TableAPI {

    String BASE_TABLE_URL="/table";

    @PostMapping("/addColumn")
    ColumnAdditionDTO addColumnToTable(@RequestBody ColumnAdditionDTO columnAdditionDTO) throws SQLException;

    @GetMapping("/{tableName}")
    TableData getTableData(@PathVariable String tableName) throws SQLException;

    @PostMapping("/addRecord")
    RecordAdditionDTO addRecord(@RequestBody RecordAdditionDTO recordAdditionDTO);




    }
