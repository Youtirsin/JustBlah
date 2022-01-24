package com.youtirsin.blah.message;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

import com.youtirsin.blah.data.Result;
import com.youtirsin.blah.data.ResultCode;
import com.youtirsin.blah.data.ResultTool;
import com.youtirsin.blah.group.GroupService;
import com.youtirsin.blah.group.TalkGroup;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private UserService userService;
  @Autowired
  private MessageService messageService;
  @Autowired
  private GroupService groupService;


  @GetMapping(path = "/api/message/send")
  public Result<Integer> sendMessage(@RequestParam(required = true) String toName, 
                  @RequestParam(required = true) String msg, Principal principal ) {
    User from = null;
    User to = null;
    try {
      from = userService.getUserWithName(principal.getName());
    } catch (Exception e) {
      return ResultTool.fail(ResultCode.USER_NOT_LOGIN);
    }
    try {
      to = userService.getUserWithName(toName);
    } catch (Exception e) {
      return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
    }
    messageService.sendMessage(from, to, msg);
    System.out.println("sent notification to: " + to.getName());
    MessageResponse messageResponse = new MessageResponse(from.getName(), to.getName(), msg, new Date());
    simpMessagingTemplate.convertAndSend("/topic/" + to.getName(), messageResponse);
    return ResultTool.success();
  }

  @GetMapping(path = "/api/message/loadfirst")
  public Result<ArrayList<MessageResponse>> loadFirstMessages(Principal principal ) {
    User user = null;

    try {
      user = userService.getUserWithName(principal.getName());
    } catch (Exception e) {
      return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
    }
    ArrayList<MessageResponse> res = messageService.fetchFirstMessages(user);
    
    return ResultTool.success(res);
  }

  @GetMapping(path = "/api/message/load")
  public Result<ArrayList<MessageResponse>> loadMessages(@RequestParam(required = true) String friendName, Principal principal ) {
    User from = null;
    User to = null;

    try {
      from = userService.getUserWithName(principal.getName());
    } catch (Exception e) {
      return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
    }
    try {
      to = userService.getUserWithName(friendName);
    } catch (Exception e) {
      return ResultTool.dataFail(ResultCode.USER_ACCOUNT_NOT_EXIST);
    }
    ArrayList<MessageResponse> res = messageService.fetchMessages(from, to);
    
    return ResultTool.success(res);
  }


  @GetMapping(path = "/api/groupmessage/send")
  public Result<Integer> sendGroupMessage(@RequestParam(required = true) String groupName, 
                  @RequestParam(required = true) String msg, Principal principal ) {
    User from = null;
    TalkGroup group =  null;
    try {
      from = userService.getUserWithName(principal.getName());
    } catch (Exception e) {
      return ResultTool.fail(ResultCode.USER_NOT_LOGIN);
    }
    try {
      group = groupService.getGroup(groupName);
    } catch (Exception e) {
      return ResultTool.fail(ResultCode.GROUP_NOT_EXIST);
    }
    messageService.sendGroupMessage(from, group, msg);
    System.out.println("sent notification to: " + group.getName());
    GroupMessageResponse messageResponse = new GroupMessageResponse(from.getName(), group.getName(), msg, new Date());
    simpMessagingTemplate.convertAndSend("/topic/" + group.getName(), messageResponse);
    return ResultTool.success();
  }

  @GetMapping(path = "/api/groupmessage/loadfirst")
  public Result<ArrayList<GroupMessageResponse>> loadFirstGroupMessages(Principal principal ) {
    User user = null;

    try {
      user = userService.getUserWithName(principal.getName());
    } catch (Exception e) {
      return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
    }
    ArrayList<GroupMessageResponse> res = messageService.fetchFirstGroupMessages(user);
    
    return ResultTool.success(res);
  }

  @GetMapping(path = "/api/groupmessage/load")
  public Result<ArrayList<GroupMessageResponse>> loadGroupMessages(@RequestParam(required = true) String groupName, Principal principal ) {
    TalkGroup group = null;
    try {
      group = groupService.getGroup(groupName);
    } catch (Exception e) {
      return ResultTool.dataFail(ResultCode.GROUP_NOT_EXIST);
    }
    ArrayList<GroupMessageResponse> res = messageService.fetchGroupMessages(group);
    
    return ResultTool.success(res);
  }
}