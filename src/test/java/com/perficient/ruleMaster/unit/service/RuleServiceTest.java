package com.perficient.ruleMaster.unit.service;

import com.perficient.ruleMaster.dto.RuleDTO;
import com.perficient.ruleMaster.error.exception.RuleMasterException;
import com.perficient.ruleMaster.maper.RuleMapper;
import com.perficient.ruleMaster.maper.RuleMapperImpl;
import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.repository.RuleRepository;
import com.perficient.ruleMaster.service.RuleService;
import com.perficient.ruleMaster.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RuleServiceTest {

    private RuleRepository ruleRepository;

    private RuleService ruleService;

    private RuleMapper ruleMapper;

    private TableService tableService;

    @BeforeEach
    private void init(){
        ruleRepository = mock(RuleRepository.class);
        ruleMapper = spy(RuleMapperImpl.class);
        ruleService = new RuleService(ruleRepository, ruleMapper, tableService);
    }

    @Test
    public void testCreateRule(){

        RuleDTO ruleToCreate = defaultRule();
        when(ruleRepository.findByName(ruleToCreate.getRuleName())).thenReturn(Optional.empty());

        ruleService.createRule(ruleToCreate);

        verify(ruleRepository, times(1)).save(any());
        verify(ruleRepository, times(1)).findByName(any());
    }

    @Test
    public void testCreateRuleWithNameThatAlreadyExists(){

        RuleDTO ruleToCreate = defaultRule();
        Rule rule = ruleMapper.fromRuleDTO(ruleToCreate);

        when(ruleRepository.findByName(ruleToCreate.getRuleName())).thenReturn(Optional.of(rule));

        var exception = assertThrows(RuleMasterException.class, () -> ruleService.createRule(ruleToCreate));

        assertEquals(409, exception.getStatus().value());
        assertEquals("Rule with name "+ruleToCreate.getRuleName()+" already exists", exception.getMessage());
    }

    @Test
    public void testGetRule(){

        RuleDTO ruleToCreate = defaultRule();
        Rule rule = ruleMapper.fromRuleDTO(ruleToCreate);
        when(ruleRepository.findByName(defaultRule().getRuleName())).thenReturn(Optional.of(rule));

        RuleDTO ruleToGet = ruleService.getRuleByName(rule.getRuleName());

        assertEquals("Rule 1", ruleToGet.getRuleName());
        assertEquals("name === 'John' && age > 25", ruleToGet.getRuleDefinition());
    }

    @Test
    public void testGetRuleThatNotExists(){

        when(ruleRepository.findByName(defaultRule().getRuleName())).thenReturn(Optional.empty());

        var exception = assertThrows(RuleMasterException.class, () -> ruleService.getRuleByName(defaultRule().getRuleName()));

        assertEquals(404, exception.getStatus().value());
        assertEquals("The rule with name "+defaultRule().getRuleName()+" not exists", exception.getMessage());
    }

    private RuleDTO defaultRule(){
        return RuleDTO.builder()
                .ruleName("Rule 1")
                .ruleDefinition("name === 'John' && age > 25")
                .build();
    }
}
