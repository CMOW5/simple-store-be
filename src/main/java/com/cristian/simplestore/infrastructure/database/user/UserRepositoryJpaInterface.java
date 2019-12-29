package com.cristian.simplestore.infrastructure.database.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpaInterface extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);

}
