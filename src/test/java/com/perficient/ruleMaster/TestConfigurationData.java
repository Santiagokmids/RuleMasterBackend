package com.perficient.ruleMaster;

import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.model.RuleMasterUser;
import com.perficient.ruleMaster.repository.RuleRepository;
import com.perficient.ruleMaster.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@TestConfiguration
public class TestConfigurationData {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RuleRepository ruleRepository){

        RuleMasterUser user1 = RuleMasterUser.builder()
                .userId(UUID.randomUUID())
                .name("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .password("password")
                .role("Admin")
                .build();

        RuleMasterUser user2 = RuleMasterUser.builder()
                .userId(UUID.randomUUID())
                .name("Marie")
                .lastName("Doe")
                .email("mariedoe@email.com")
                .password("password")
                .role("Rule manager")
                .build();

        RuleMasterUser user3 = RuleMasterUser.builder()
                .userId(UUID.randomUUID())
                .name("John")
                .lastName("Johnson")
                .email("johnson@email.com")
                .password("password")
                .role("Column manager")
                .build();

        RuleMasterUser user4 = RuleMasterUser.builder()
                .userId(UUID.randomUUID())
                .name("Sara")
                .lastName("White")
                .email("white@email.com")
                .password("password")
                .role("Record manager")
                .build();

        Rule rule1 = Rule.builder()
                .ruleId(UUID.randomUUID())
                .ruleName("Rule 1")
                .ruleDefinition("name === 'John' && age > 25")
                .build();

        Rule rule2 = Rule.builder()
                .ruleId(UUID.randomUUID())
                .ruleName("Rule 2")
                .ruleDefinition("age > 40")
                .build();

        return args -> {
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            ruleRepository.save(rule1);
            ruleRepository.save(rule2);
        };
    }
}
