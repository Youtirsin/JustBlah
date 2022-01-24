package com.youtirsin.blah.invitation;

public class FriendInvitationRequest {
	private final String target;

	public FriendInvitationRequest() {
		target = "";
	}
	public FriendInvitationRequest(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}
	
}
