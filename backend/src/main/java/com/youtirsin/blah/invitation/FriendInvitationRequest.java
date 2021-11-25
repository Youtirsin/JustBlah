package com.youtirsin.blah.invitation;

public class FriendInvitationRequest {
	private final String from;
	private final String to;
	
	public FriendInvitationRequest(String from, String to) {
		this.from = from;
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
}
