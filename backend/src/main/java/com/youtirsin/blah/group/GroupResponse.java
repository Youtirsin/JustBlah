package com.youtirsin.blah.group;

import java.util.Date;

public class GroupResponse {
  public final String name;
  public final String founder;
  public final Date timestamp;
  public GroupResponse(String name, String founder, Date timestamp) {
    this.name = name;
    this.founder = founder;
    this.timestamp = timestamp;
  }
  public String getFounder() {
    return founder;
  }
  public String getName() {
    return name;
  }
  public Date getTimestamp() {
    return timestamp;
  }
}
