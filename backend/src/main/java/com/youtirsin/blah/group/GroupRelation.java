package com.youtirsin.blah.group;


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
				columnNames = { "member_id", "group_id" }
		)
)
public class GroupRelation {

	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "group_relation_sequence",
						sequenceName = "group_relation_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "group_relation_sequence")
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "MEMBER_ID_FK"))
	private User member;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "GROUP_ID_FK"))
	private TalkGroup group;

	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	public GroupRelation() {
	}

	public GroupRelation(User member, TalkGroup group) {
		this.member = member;
		this.group = group;
		this.timestamp = new Date();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

  public TalkGroup getGroup() {
    return group;
  }
  public User getMember() {
    return member;
  }
  public void setGroup(TalkGroup group) {
    this.group = group;
  }
  public void setMember(User member) {
    this.member = member;
  }
  
	public Date getTimestamp() {
		return timestamp;
	}
}
