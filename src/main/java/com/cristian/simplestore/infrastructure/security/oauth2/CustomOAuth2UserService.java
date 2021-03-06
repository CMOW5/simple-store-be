package com.cristian.simplestore.infrastructure.security.oauth2;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cristian.simplestore.infrastructure.database.user.UserEntity;
import com.cristian.simplestore.infrastructure.database.user.UserRepositoryJpaInterface;
import com.cristian.simplestore.infrastructure.security.UserPrincipal;
import com.cristian.simplestore.infrastructure.security.oauth2.exceptions.OAuth2AuthenticationProcessingException;
import com.cristian.simplestore.infrastructure.security.oauth2.user.OAuth2UserInfo;
import com.cristian.simplestore.infrastructure.security.oauth2.user.OAuth2UserInfoFactory;

/**
 * If the OAuth2 callback is successful and it contains the authorization code, Spring Security will
 * exchange the authorization_code for an access_token and invoke the customOAuth2UserService
 * specified in the above SecurityConfig.
 * 
 * this service retrieves the details of the authenticated user and creates a new entry in the
 * database or updates the existing entry with the same email.
 * 
 * @author bit5
 *
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Autowired
  private UserRepositoryJpaInterface userRepository;

  /**
   * This method is called after an access token is obtained from the OAuth2 provider
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the
      // OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
        oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<UserEntity> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
    UserEntity user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider().equals(
          AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException(
            "Looks like you're signed up with " + user.getProvider() + " account. Please use your "
                + user.getProvider() + " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
	  UserEntity user = new UserEntity();

    user.setProvider(
        AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
    user.setProviderId(oAuth2UserInfo.getId());
    user.setName(oAuth2UserInfo.getName());
    user.setEmail(oAuth2UserInfo.getEmail());
    user.setImageUrl(oAuth2UserInfo.getImageUrl());
    return userRepository.save(user);
  }

  private UserEntity updateExistingUser(UserEntity existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setName(oAuth2UserInfo.getName());
    existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    return userRepository.save(existingUser);
  }

}
