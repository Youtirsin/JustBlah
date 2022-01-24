package com.youtirsin.blah.message;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GroupMessageRepository extends CrudRepository<GroupMessage, Long>{
	Iterable<GroupMessage> findAllByTalkGroupId(Long group_id);
  
	@Query(value = "select * from group_message where group_id=:group_id order by id desc limit 1", nativeQuery = true)
	Optional<GroupMessage> findLastByGroupId(Long group_id);
}
