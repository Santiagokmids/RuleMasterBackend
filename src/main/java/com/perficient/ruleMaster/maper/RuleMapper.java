package com.perficient.ruleMaster.maper;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.model.Rule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    Rule fromRuleDTO(RuleDTO ruleDTO);

    RuleDTO fromRule(Rule rule);
}
