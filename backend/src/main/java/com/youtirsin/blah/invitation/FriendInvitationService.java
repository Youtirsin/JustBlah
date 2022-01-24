package com.youtirsin.blah.invitation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtirsin.blah.user.User;

@Service
public class FriendInvitationService {
	@Autowired
	private FriendInvitationRepository friendInvitationRepository;
	
	public void saveInvitation(FriendInvitation invitation) throws Exception{
		// throw exception if invitation already exists
		try {
			friendInvitationRepository.save(invitation);
		}catch (Exception e) {
			throw new Exception();
		}
	}

	public void rejectInvitation(FriendInvitation invitation) {
		invitation.setRejected(true);
		friendInvitationRepository.save(invitation);
	}

	public void deleteInvitation(FriendInvitation invitation) {
		friendInvitationRepository.delete(invitation);
	}
	
	public FriendInvitation getInvitationWithUsers(User from, User to) throws Exception {
		// throws exception if the invitation is invalid
		Optional<FriendInvitation> invitation = friendInvitationRepository
									.findByFromIdAndToId(from.getId(), to.getId());
		if (!invitation.isPresent()) {
			throw new Exception();
		}
		return invitation.get();
	}

	public ArrayList<FriendInvitationResponse> loadMyInvitations(User to) {
		ArrayList<FriendInvitation> searched = friendInvitationRepository.findAllByToId(to.getId());

		ArrayList<FriendInvitationResponse> res = new ArrayList<>(searched.size());
		for (FriendInvitation invitation : searched) {
			if (!invitation.isRejected()) {
				FriendInvitationResponse response = new FriendInvitationResponse(invitation.getFrom().getName(), invitation.getTo().getName());
				res.add(response);
			}			
		}
		return res;
	}
	
	public ArrayList<FriendInvitationResponse> loadInvitations(User from) {
		ArrayList<FriendInvitation> searched = friendInvitationRepository.findAllByFromId(from.getId());
		ArrayList<FriendInvitationResponse> res = new ArrayList<>(searched.size());
		for (FriendInvitation invitation : searched) {
			FriendInvitationResponse response = new FriendInvitationResponse(invitation.getFrom().getName(), invitation.getTo().getName());
			res.add(response);
		}
		return res;
	}
}
