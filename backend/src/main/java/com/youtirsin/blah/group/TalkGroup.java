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
				columnNames = { "founder_id", "name" }
		)
)
public class TalkGroup {

	@Id
	@SequenceGenerator(allocationSize = 1,
						name = "group_sequence",
						sequenceName = "group_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
						generator = "group_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "founder_id", foreignKey = @ForeignKey(name = "FOUNDER_ID_FK"))
	private User founder;

	@Column(name = "name")
  private String name;
	
	@Column(name = "`timestamp`")
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	public TalkGroup() {
	}

	public TalkGroup(User founder, String name) {
		this.founder = founder;
		this.name = name;
		this.timestamp = new Date();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

  public User getFounder() {
    return founder;
  }
  public void setFounder(User founder) {
    this.founder = founder;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
	public Date getTimestamp() {
		return timestamp;
	}
}
