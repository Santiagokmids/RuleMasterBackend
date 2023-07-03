package com.perficient.ruleMaster.controller;

import com.perficient.ruleMaster.api.TableAPI;
import com.perficient.ruleMaster.dto.ColumnAdditionDTO;
import com.perficient.ruleMaster.dto.RecordAdditionDTO;
import com.perficient.ruleMaster.model.TableData;
import com.perficient.ruleMaster.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static com.perficient.ruleMaster.api.TableAPI.BASE_TABLE_URL;

@RestController
@RequestMapping(BASE_TABLE_URL)
@AllArgsConstructor
public class TableController implements TableAPI {
    private final TableService tableService;

    @Override
    public ColumnAdditionDTO addColumnToTable(ColumnAdditionDTO columnAdditionDTO) throws SQLException {
        return tableService.addColumnToTable(columnAdditionDTO);
    }
    @Override
    public TableData getTableData(String tableName) throws SQLException {
        return tableService.getTableData(tableName);
    }
    @Override
    public RecordAdditionDTO addRecord(RecordAdditionDTO recordAdditionDTO) {
        return tableService.addRecord(recordAdditionDTO);
    }
}