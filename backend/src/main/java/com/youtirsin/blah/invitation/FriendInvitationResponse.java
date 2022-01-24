package com.youtirsin.blah.invitation;

public class FriendInvitationResponse {
	private final String from;
	private final String to;
	public FriendInvitationResponse() {
		from = null;
		to = null;
	}
	public FriendInvitationResponse(String f, String t) {
		from = f;
		to = t;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
}
