package com.cristian.simplestore.basetest;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.utils.DbCleaner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseE2ETest extends BaseTest {

	@Autowired
	DbCleaner dbCleaner;

	@Before
	public void setUp() {
		cleanUpDb();
	}

	@After
	public void tearDown() {
		cleanUpDb();
	}

	protected void cleanUpDb() {
		dbCleaner.cleanAllTables();
	}
}