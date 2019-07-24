package com.cristian.simplestore.infrastructure.adapters.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristian.simplestore.infrastructure.adapters.repository.entities.UserEntity;

public interface UserRepositoryJpaInterface extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);

}
