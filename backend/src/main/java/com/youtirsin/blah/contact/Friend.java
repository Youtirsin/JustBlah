package com.youtirsin.blah.contact;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.youtirsin.blah.user.User;

@Entity
public class Friend {

	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "friend_sequence",
						sequenceName = "friend_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "friend_sequence")
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private User user1;
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private User user2;
	
	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	public Friend(User user1, User user2) {
		this.user1 = user1;
		this.user2 = user2;
		this.timestamp = new Date();
	}

	public User getUser1() {
		return user1;
	}



	public void setUser1(User user1) {
		this.user1 = user1;
	}



	public User getUser2() {
		return user2;
	}



	public void setUser2(User user2) {
		this.user2 = user2;
	}



	public Date getTimestamp() {
		return timestamp;
	}
}
