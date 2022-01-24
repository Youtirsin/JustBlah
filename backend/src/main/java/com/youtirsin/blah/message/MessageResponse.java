package com.youtirsin.blah.message;

import java.util.Date;

public class MessageResponse {
  private final String from;
  private final String to;
  private final String msg;
  private Date timestamp;
  public MessageResponse(String from, String to, String msg, Date timestamp) {
    this.from = from;
    this.to = to;
    this.msg = msg;
    this.timestamp = timestamp;
  }
  public String getFrom() {
    return from;
  }
  public String getTo() {
    return to;
  }
  public String getMsg() {
    return msg;
  }
  public Date getTimestamp() {
    return timestamp;
  }
}
