package com.youtirsin.blah.invitation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.youtirsin.blah.user.User;


@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(
				columnNames = { "from", "to" }
		)
)
public class FriendInvitation {
	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "friend_invite_sequence",
						sequenceName = "friend_invite_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "friend_invite_sequence")
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private User from;
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private User to;
	private boolean rejected;
	
	public FriendInvitation() {
	}

	public FriendInvitation(User from, User to) {
		this.from = from;
		this.to = to;
		this.rejected = false;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	
}
