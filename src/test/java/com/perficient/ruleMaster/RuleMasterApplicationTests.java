package com.perficient.ruleMaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles="test")
@Import(TestConfigurationData.class)
class RuleMasterApplicationTests {

	@Test
	void contextLoads() {
	}

}
