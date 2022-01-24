package com.youtirsin.blah.message;

import java.util.Date;

public class GroupMessageResponse {
  private final String from;
  private final String group;
  private final String msg;
  private Date timestamp;
  public GroupMessageResponse(String from, String group, String msg, Date timestamp) {
    this.from = from;
    this.group = group;
    this.msg = msg;
    this.timestamp = timestamp;
  }
  public String getFrom() {
    return from;
  }
  public String getGroup() {
    return group;
  }
  public String getMsg() {
    return msg;
  }
  public Date getTimestamp() {
    return timestamp;
  }
}
