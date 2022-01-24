package com.youtirsin.blah.friend;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.youtirsin.blah.user.User;

@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(
				columnNames = { "user1_id", "user2_id" }
		)
)
public class Friend {

	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "friend_sequence",
						sequenceName = "friend_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "friend_sequence")
	private Long id;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user1_id", foreignKey = @ForeignKey(name = "USER1_ID_FK"))
	private User user1;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user2_id", foreignKey = @ForeignKey(name = "USER2_ID_FK"))
	private User user2;
	
	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	public Friend() {
	}

	public Friend(User user1, User user2) {
		this.user1 = user1;
		this.user2 = user2;
		this.timestamp = new Date();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
