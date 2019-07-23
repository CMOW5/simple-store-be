package com.cristian.simplestore.persistence.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.User;
// TODO: delete this class
@Component
public class userrepoimpl implements UserRepository {

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User existingUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
