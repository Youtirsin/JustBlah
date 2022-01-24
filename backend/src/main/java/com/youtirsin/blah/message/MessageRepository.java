package com.youtirsin.blah.message;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>{
	@Query(value = "select * from message where (from_id=:id1 and to_id=:id2) or (from_id=:id2 and to_id=:id1)", nativeQuery = true)
	Iterable<Message> findAllByIdAsFromOrTo(Long id1, Long id2);

	@Query(value = "select * from message where (from_id=:id1 and to_id=:id2) or (from_id=:id2 and to_id=:id1) order by id desc limit 1", nativeQuery = true)
	Optional<Message> findOneByIdAsFromOrTo(Long id1, Long id2);
	// Iterable<Message> findAllByFromIdAndToId(Long from_id, Long to_id);
	// @Query(value = "select * from message where ", nativeQuery = true)
	// ArrayList<String> findAllNamesById(Long id);
	Iterable<Message> findAllByFromId(Long id);
	Iterable<Message> findAllByToId(Long id);
}
