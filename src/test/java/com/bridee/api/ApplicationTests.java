package com.bridee.api;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DBRider
@DBUnit(leakHunter = true)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
