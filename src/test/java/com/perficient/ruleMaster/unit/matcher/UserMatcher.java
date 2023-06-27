package com.perficient.ruleMaster.unit.matcher;

import com.perficient.ruleMaster.model.RuleMasterUser;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

public class UserMatcher implements ArgumentMatcher<RuleMasterUser> {

    private RuleMasterUser userLeft;

    public UserMatcher(RuleMasterUser userLeft){
        this.userLeft = userLeft;
    }

    @Override
    public boolean matches(RuleMasterUser userRight) {
        return userRight.getUserId() != null &&
                Objects.equals(userLeft.getName(), userRight.getName()) &&
                Objects.equals(userLeft.getLastName(), userRight.getLastName()) &&
                Objects.equals(userLeft.getEmail(), userRight.getEmail()) &&
                Objects.equals(userLeft.getPassword(), userRight.getPassword()) &&
                Objects.equals(userLeft.getRole(), userRight.getRole());
    }
}
