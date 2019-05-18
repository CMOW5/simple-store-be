package com.cristian.simplestore.utils.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.AuthTestUtils;

@Component
public class TestTokenGenerator implements TokenGenerator {

  @Autowired
  AuthTestUtils authTestUtils;
  
  @Override
  public String generate() {
    return authTestUtils.generateToken();
  }
}
