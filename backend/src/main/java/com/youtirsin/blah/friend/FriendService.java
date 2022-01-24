package com.youtirsin.blah.friend;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtirsin.blah.user.User;


@Service
public class FriendService {
	@Autowired
	private FriendRepository friendRepository;


	public Boolean isFriend(User user1, User user2) {
		Optional<Friend> res = friendRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId());
		if(res.isEmpty()) {
			return false;
		}
		return true;
		// Iterable<Friend> res = friendRepository.findAllByUser1Id(user1.getId());
		// for (Friend friend : res) {
		// 	if (friend.getUser2().getName().equals(user2.getName())) {
		// 		return true;
		// 	}
		// }
		// return false;
	}
	
	public void makeFriend(User user1, User user2) throws Exception {
		// failed if already been friend
		Friend friend = new Friend(user1, user2);
		Friend friend2 = new Friend(user2, user1);
		try {
			friendRepository.save(friend);
			friendRepository.save(friend2);
		}catch (Exception e) {
			System.out.println("exception here");
			throw new Exception();
		}
	}
	public ArrayList<String> loadFriendNames(User user) {
		ArrayList<Friend> searched = friendRepository.findAllByUser1Id(user.getId());
		ArrayList<String> res = new ArrayList<>(searched.size());
		for (Friend friend : searched) {
			String name = new String(friend.getUser2().getName());
			res.add(name);
		}
		return res;
	}
	public void deleteFriend(User user1, User user2) {
		Optional<Friend> friend = friendRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId());
		Optional<Friend> friend2 = friendRepository.findByUser1IdAndUser2Id(user2.getId(), user1.getId());
		if (!friend.isEmpty()) {
			friendRepository.delete(friend.get());
		}
		if (!friend2.isEmpty()) {
			friendRepository.delete(friend2.get());
		}
	}
}
