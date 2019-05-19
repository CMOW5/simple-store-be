package com.cristian.simplestore.utils.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestTokenGenerator implements TokenGenerator {

  @Autowired
  AuthTestUtils authTestUtils;
  
  @Override
  public String generate() {
    return authTestUtils.generateToken();
  }
}
