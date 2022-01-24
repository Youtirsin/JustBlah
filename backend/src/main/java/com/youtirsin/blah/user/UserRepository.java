package com.youtirsin.blah.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByName(String name);
	// @Query(value = "select name from user where name like '%:word%'", nativeQuery = true)
	// ArrayList<String> findAllWithWord(String word);
}