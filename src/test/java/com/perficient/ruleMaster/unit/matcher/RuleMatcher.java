package com.perficient.ruleMaster.unit.matcher;

import com.perficient.ruleMaster.model.Rule;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

public class RuleMatcher implements ArgumentMatcher<Rule> {

    private Rule ruleLeft;

    public RuleMatcher(Rule ruleLeft){
        this.ruleLeft = ruleLeft;
    }

    @Override
    public boolean matches(Rule ruleRight) {
        return ruleRight.getRuleId() != null &&
                Objects.equals(ruleLeft.getRuleName(), ruleRight.getRuleName()) &&
                Objects.equals(ruleLeft.getRuleDefinition(), ruleRight.getRuleDefinition());
    }
}
