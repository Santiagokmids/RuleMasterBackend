package com.perficient.ruleMaster;

import com.perficient.ruleMaster.model.Rule;
import com.perficient.ruleMaster.model.RuleMasterUser;
import com.perficient.ruleMaster.repository.RuleRepository;
import com.perficient.ruleMaster.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class RuleMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuleMasterApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, RuleRepository ruleRepository, PasswordEncoder passwordEncoder){

		RuleMasterUser user1 = RuleMasterUser.builder()
				.userId(UUID.randomUUID())
				.name("John")
				.lastName("Doe")
				.email("johndoe@email.com")
				.password(passwordEncoder.encode("password"))
				.role("Administrador")
				.build();

		RuleMasterUser user2 = RuleMasterUser.builder()
				.userId(UUID.randomUUID())
				.name("Marie")
				.lastName("Doe")
				.email("mariedoe@email.com")
				.password(passwordEncoder.encode("password"))
				.role("Gestor_de_reglas")
				.build();

		RuleMasterUser user3 = RuleMasterUser.builder()
				.userId(UUID.randomUUID())
				.name("John")
				.lastName("Johnson")
				.email("johnson@email.com")
				.password(passwordEncoder.encode("password"))
				.role("Gestor_de_columnas")
				.build();

		RuleMasterUser user4 = RuleMasterUser.builder()
				.userId(UUID.randomUUID())
				.name("Sara")
				.lastName("White")
				.email("white@email.com")
				.password(passwordEncoder.encode("password"))
				.role("Gestor_de_registros")
				.build();

		Rule rule1 = Rule.builder()
				.ruleId(UUID.randomUUID())
				.ruleName("Rule 1")
				.ruleDefinition("name === 'John' && age > 25")
				.ruleTransformed("name === 'John' && age > 25")
				.build();

		Rule rule2 = Rule.builder()
				.ruleId(UUID.randomUUID())
				.ruleName("Rule 2")
				.ruleDefinition("age > 40")
				.ruleTransformed("age > 40")
				.build();

		return args -> {
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
			//ruleRepository.save(rule1);
			//ruleRepository.save(rule2);
		};
	}

}
