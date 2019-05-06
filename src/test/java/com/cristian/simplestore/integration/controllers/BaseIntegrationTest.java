package com.cristian.simplestore.integration.controllers;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.utils.DbCleaner;

public abstract class BaseIntegrationTest extends BaseTest {
  
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
