package com.youtirsin.blah.invitation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.message.ResultCode;
import com.youtirsin.blah.message.ResultTool;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserRepository;

@Service
public class FriendInvitationService {
	@Autowired
	private FriendInvitationRepository friendInvitationRepository;
	@Autowired
	private UserRepository userRepository;
	
	public MessageResult saveInvitation(FriendInvitationRequest request) {
		Optional<User> from = userRepository.findByName(request.getFrom());
		Optional<User> to = userRepository.findByName(request.getTo());
		
		if (!from.isPresent() || !to.isPresent()) {
			return ResultTool.fail(ResultCode.INVITATION_INVALID);
		}
		try {
			friendInvitationRepository.save(new FriendInvitation(from.get(), to.get()));
		}catch (Exception e) {
			return ResultTool.fail(ResultCode.INVITATION_ALREADY_EXIST);
		}
		
		return ResultTool.success();
	}

	public MessageResult rejectInvitation(FriendInvitationRequest request) {
		Optional<User> from = userRepository.findByName(request.getFrom());
		Optional<User> to = userRepository.findByName(request.getTo());
		
		if (!from.isPresent() || !to.isPresent()) {
			return ResultTool.fail(ResultCode.INVITATION_INVALID);
		}
		
		Optional<FriendInvitation> invitation = friendInvitationRepository
						.findWithFromAndTo(from.get().getId(), to.get().getId());
		if (invitation.isPresent()) {
			invitation.get().setRejected(true);
		}
		return ResultTool.success();
	}

	public MessageResult deleteInvitation(FriendInvitationRequest invitationRequest) {
		return null;
	}
}
