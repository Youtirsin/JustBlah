package com.youtirsin.blah.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtirsin.blah.message.MessageResult;

@RestController
public class InvitationController {
	@Autowired
	private FriendInvitationService friendInvitationService;
	
	@GetMapping(path = "/api/invite/send")
	public MessageResult sendInvitation(@RequestBody FriendInvitationRequest invitationRequest) { 
		return friendInvitationService.saveInvitation(invitationRequest);
	}
	
	@GetMapping(path = "/api/invite/reject")
	public MessageResult rejectInvitation(@RequestBody FriendInvitationRequest invitationRequest) {
		return friendInvitationService.rejectInvitation(invitationRequest);
	}
	@GetMapping(path = "/api/invite/accept")
	public MessageResult acceptInvitation(@RequestBody FriendInvitationRequest invitationRequest) {
		
		return friendInvitationService.deleteInvitation(invitationRequest);
	}
}
