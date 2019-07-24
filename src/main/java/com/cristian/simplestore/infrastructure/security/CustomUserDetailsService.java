package com.cristian.simplestore.infrastructure.security;


import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristian.simplestore.infrastructure.adapters.repository.entities.UserEntity;
import com.cristian.simplestore.infrastructure.adapters.repository.user.UserRepositoryJpaInterface;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepositoryJpaInterface userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) {
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
	  UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Could not find the User with id" + id));

    return UserPrincipal.create(user);
  }
}
