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

import com.youtirsin.blah.group.TalkGroup;
import com.youtirsin.blah.user.User;


@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(
				columnNames = { "id", "from_id", "group_id" }
		)
)
public class GroupMessage {
	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "group_message_sequence",
						sequenceName = "group_message_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "group_message_sequence")
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "from_id", foreignKey = @ForeignKey(name = "FROM_ID_FK"))
	private User from;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "GROUP_ID_FK"))
	private TalkGroup talkGroup;
	
	private String msg;
	
	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;
	
	public GroupMessage() {
	}

	public GroupMessage(User from, TalkGroup talkGroup, String msg) {
		this.from = from;
		this.talkGroup = talkGroup;
		this.msg = msg;
		this.timestamp = new Date();
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

  public TalkGroup getTalkGroup() {
    return talkGroup;
  }
  public void setTalkGroup(TalkGroup talkGroup) {
    this.talkGroup = talkGroup;
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
