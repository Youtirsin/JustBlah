package com.youtirsin.blah.message;

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
				columnNames = { "id", "from_id", "to_id" }
		)
)
public class Message {
	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "message_sequence",
						sequenceName = "message_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "message_sequence")
	private Long id;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "from_id", foreignKey = @ForeignKey(name = "FROM_ID_FK"))
	private User from;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "to_id", foreignKey = @ForeignKey(name = "TO_ID_FK"))
	private User to;
	
	private String msg;
	
	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	public Message() {
	}

	public Message(User from, User to, String msg) {
		this.from = from;
		this.to = to;
		this.msg = msg;
		this.timestamp = new Date();
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
