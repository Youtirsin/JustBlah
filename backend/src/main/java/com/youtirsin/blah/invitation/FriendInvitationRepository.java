package com.youtirsin.blah.invitation;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FriendInvitationRepository extends CrudRepository<FriendInvitation, Long>{
	@Query(value = "select * from friend_invitation where from_id = :from_id and to_id = :to_id", nativeQuery = true)
	Optional<FriendInvitation> findWithFromAndTo(Long from_id, Long to_id);
}
