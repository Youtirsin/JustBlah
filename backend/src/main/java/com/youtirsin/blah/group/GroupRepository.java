package com.youtirsin.blah.group;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<TalkGroup, Long>{
  Optional<TalkGroup> findByName(String name);
}
