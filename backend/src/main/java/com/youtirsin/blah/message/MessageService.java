package com.youtirsin.blah.message;

import java.util.ArrayList;
import java.util.Optional;

import com.youtirsin.blah.group.GroupRelation;
import com.youtirsin.blah.group.GroupRelationRepository;
import com.youtirsin.blah.group.TalkGroup;
import com.youtirsin.blah.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private GroupMessageRepository groupMessageRepository;
  @Autowired
  private GroupRelationRepository groupRelationRepository;

  public void sendMessage(User from, User to, String msg) {
    Message message = new Message(from, to, msg);
    messageRepository.save(message);
  }

  public void sendGroupMessage(User from, TalkGroup group, String msg) {
    GroupMessage message = new GroupMessage(from, group, msg);
    groupMessageRepository.save(message);
  }

  public ArrayList<MessageResponse> fetchMessages(User from, User to) {
    ArrayList<MessageResponse> res = new ArrayList<MessageResponse>();
    Iterable<Message> searched = messageRepository.findAllByIdAsFromOrTo(from.getId(), to.getId());
    for (Message message : searched) {
      MessageResponse response = new MessageResponse(message.getFrom().getName(), 
                    message.getTo().getName(), message.getMsg(), message.getTimestamp());
      res.add(response);
    }
    return res;
  }

  public ArrayList<GroupMessageResponse> fetchGroupMessages(TalkGroup group) {
    ArrayList<GroupMessageResponse> res = new ArrayList<GroupMessageResponse>();

    Iterable<GroupMessage> searched = groupMessageRepository.findAllByTalkGroupId(group.getId());

    for (GroupMessage message: searched) {
      GroupMessageResponse response = new GroupMessageResponse(message.getFrom().getName(), 
              message.getTalkGroup().getName(), message.getMsg(), message.getTimestamp());
      res.add(response);
    }

    return res;
  }

  public ArrayList<GroupMessageResponse> fetchFirstGroupMessages(User user) {
    
    ArrayList<GroupMessageResponse> res = new ArrayList<GroupMessageResponse>();

    Iterable<GroupRelation> groups = groupRelationRepository.findByMemberId(user.getId());

    for (GroupRelation groupRelation : groups) {
      TalkGroup group = groupRelation.getGroup();
      
      Optional<GroupMessage> optional = groupMessageRepository.findLastByGroupId(group.getId());
      if(optional.isPresent()){
        GroupMessage message = optional.get();
        GroupMessageResponse response = new GroupMessageResponse(message.getFrom().getName(), 
                message.getTalkGroup().getName(), message.getMsg(), message.getTimestamp());
        res.add(response);
      }

    }
    return res;
  }

  public ArrayList<MessageResponse> fetchFirstMessages(User user) {
    ArrayList<MessageResponse> res = new ArrayList<MessageResponse>();
    ArrayList<User> friends = new ArrayList<User>();

    Iterable<Message> searched = messageRepository.findAllByFromId(user.getId());
    for (Message message : searched) {
      if (!friends.contains(message.getTo())) {
        friends.add(message.getTo());
      }
    }

    searched = messageRepository.findAllByToId(user.getId());
    for (Message message : searched) {
      if (!friends.contains(message.getFrom())) {
        friends.add(message.getFrom());
      }
    }

    for (User friend : friends) {
      Optional<Message> optional = messageRepository.findOneByIdAsFromOrTo(user.getId(), friend.getId());
      Message message = optional.get();
      MessageResponse response = new MessageResponse(message.getFrom().getName(), 
                    message.getTo().getName(), message.getMsg(), message.getTimestamp());
      res.add(response);
    }
    return res;
  }
}
