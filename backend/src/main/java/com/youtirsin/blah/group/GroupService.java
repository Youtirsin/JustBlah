package com.youtirsin.blah.group;


import java.util.ArrayList;
import java.util.Optional;

import com.youtirsin.blah.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private GroupRelationRepository groupRelationRepository;

  public void createGroup(User founder, String name) {
    TalkGroup group = new TalkGroup(founder, name);
    groupRepository.save(group);
  } 

  public TalkGroup getGroup(String name) {
    Optional<TalkGroup> searched = groupRepository.findByName(name);
    return searched.get();
  }

  public GroupResponse getGroupInfo(String name) {
    Optional<TalkGroup> searched = groupRepository.findByName(name);
    GroupResponse response = new GroupResponse(searched.get().getName(), 
          searched.get().getFounder().getName(), searched.get().getTimestamp());
    return response;
  }
  
  public ArrayList<String> searchGroup(String keyword) {
		ArrayList<String> res = new ArrayList<String>();
    Iterable<TalkGroup> searched = groupRepository.findAll();
    
		for (TalkGroup group : searched) {
			if (group.getName().contains(keyword)) {
				res.add(group.getName());
			}
		}
		return res;
  }

  public ArrayList<String> loadGroups(User user) {
		ArrayList<String> res = new ArrayList<String>();
    Iterable<GroupRelation> searched = groupRelationRepository.findByMemberId(user.getId());
    
    for (GroupRelation groupRelation : searched) {
      String name = groupRelation.getGroup().getName();
      res.add(name);
    }
		return res;
  }
  
  
  public Boolean isInGroup(User member, String name) throws Exception {
    Optional<TalkGroup> searched = groupRepository.findByName(name);
    if (searched.isEmpty()) {
      throw new Exception("group doesnt exist");
    }
		Optional<GroupRelation> res = groupRelationRepository.findByMemberIdAndGroupId(member.getId(), searched.get().getId());
		if(res.isEmpty()) {
			return false;
		}
		return true;
	}

  public void joinGroup(User user, String name) throws Exception {
    Optional<TalkGroup> searched = groupRepository.findByName(name);
    if (searched.isEmpty()) {
      throw new Exception("group doesnt exist");
    }
    GroupRelation relation = new GroupRelation(user, searched.get());
    groupRelationRepository.save(relation);
  }

  public void quitGroup(User user, String name) {
    Optional<TalkGroup> searched = groupRepository.findByName(name);
    if (!searched.isEmpty()) {
      Optional<GroupRelation> relation = groupRelationRepository.findByMemberIdAndGroupId(user.getId(), 
                    searched.get().getId());
      if (relation.isPresent()) {
        groupRelationRepository.delete(relation.get());
      }
    }
  }
}
