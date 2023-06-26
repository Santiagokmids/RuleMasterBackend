package com.perficient.ruleMaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnAdditionDTO {
    private String tableName;
    private String columnName;
    private String columnType;

}
