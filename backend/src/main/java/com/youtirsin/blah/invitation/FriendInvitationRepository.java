package com.youtirsin.blah.invitation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface FriendInvitationRepository extends CrudRepository<FriendInvitation, Long>{
//	@Query(value = "select * from friend_invitation where from_id = :from_id and to_id = :to_id", nativeQuery = true)
	Optional<FriendInvitation> findByFromIdAndToId(Long from_id, Long to_id);

//	@Query(value = "select * from friend_invitation where to_id = :from_id", nativeQuery = true)
	ArrayList<FriendInvitation> findAllByFromId(Long from_id);
//	@Query(value = "select * from friend_invitation where from_id = :to_id", nativeQuery = true)
	ArrayList<FriendInvitation> findAllByToId(Long to_id);

}
