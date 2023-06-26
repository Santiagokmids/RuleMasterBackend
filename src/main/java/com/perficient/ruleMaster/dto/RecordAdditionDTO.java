package com.perficient.ruleMaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordAdditionDTO {

    private String tableName;
    private Map<String, Object> record;

}
