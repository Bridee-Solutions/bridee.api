package com.bridee.api;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DBRider
@DBUnit(leakHunter = true)
@ActiveProfiles("test")
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
