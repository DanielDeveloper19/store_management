package com.finaltodocode.final_todocode;

import com.finaltodocode.final_todocode.integrationTestsConfig.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinalTodocodeApplicationTests extends BaseIntegrationTest {

	@Test
	void contextLoads() {
        // This will now succeed because BaseIntegrationTest
        // provides the Docker DB connection
	}

}
