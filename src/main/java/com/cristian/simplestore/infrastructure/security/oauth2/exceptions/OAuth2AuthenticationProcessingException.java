package com.cristian.simplestore.infrastructure.security.oauth2.exceptions;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
  private static final long serialVersionUID = -713999044579165213L;

  public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
    super(msg, t);
  }

  public OAuth2AuthenticationProcessingException(String msg) {
    super(msg);
  }
}
