package com.youtirsin.blah.friend;

import java.util.ArrayList;
import java.util.Optional;

// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {
	// @Query(value = "select * from friend where user1_id = :id or user2_id = :id", nativeQuery = true)
	// ArrayList<Friend> findAllByUser1IdOrUser2Id(Long id);
	// @Query(value = "select * from friend", nativeQuery = true)
	ArrayList<Friend> findAllByUser1Id(Long user1_id);
	Optional<Friend> findByUser1IdAndUser2Id(Long user1_id, Long user2_id);
}
