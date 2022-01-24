package com.youtirsin.blah.group;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GroupRelationRepository extends CrudRepository<GroupRelation, Long>{
	Optional<GroupRelation> findByMemberIdAndGroupId(Long member_id, Long group_id);
	Iterable<GroupRelation> findByMemberId(Long member_id);
}
