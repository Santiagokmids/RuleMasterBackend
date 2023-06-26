package com.perficient.ruleMaster.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableData {

    private  String tableName;
    private List<String> columnNames;
    private  List<String> columnTypes;
    private  List<Map<String, Object>> records;
}
