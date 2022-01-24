package com.youtirsin.blah.invitation;


import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtirsin.blah.data.Result;
import com.youtirsin.blah.data.ResultCode;
import com.youtirsin.blah.data.ResultTool;
import com.youtirsin.blah.friend.FriendService;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserService;

@RestController
public class InvitationController {
	@Autowired
	private FriendInvitationService friendInvitationService;
	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;
	
	@GetMapping(path = "/api/invite/send")
	public Result<Integer> sendInvitation(@RequestBody FriendInvitationRequest request, Principal principal) {
		User from = null;
		User to = null;
		try {
			from = userService.getUserWithName(principal.getName());
			to = userService.getUserWithName(request.getTarget());
		}catch(Exception e) {
			return ResultTool.fail(ResultCode.INVITATION_INVALID);
		}
		FriendInvitation invitation = new FriendInvitation(from, to);
		try {
			friendInvitationService.saveInvitation(invitation);		
			return ResultTool.success();
		}catch(Exception e) {
			return ResultTool.fail(ResultCode.INVITATION_ALREADY_EXIST);
		}
	}
	
	@GetMapping(path = "/api/invite/loadMine")
	public Result<ArrayList<FriendInvitationResponse>> loadMyInvitations(Principal principal) {
		User to = null;
		try {
			to = userService.getUserWithName(principal.getName());
		}catch(Exception e) {
			return ResultTool.dataFail(ResultCode.USER_ACCOUNT_NOT_EXIST);
		}
		ArrayList<FriendInvitationResponse> res = friendInvitationService.loadMyInvitations(to);
		return ResultTool.success(res);
	}
	
	@GetMapping(path = "/api/invite/load")
	public Result<ArrayList<FriendInvitationResponse>> loadInvitations(Principal principal) {
		User from = null;
		try {
			from = userService.getUserWithName(principal.getName());
		}catch(Exception e) {
			return ResultTool.dataFail(ResultCode.USER_ACCOUNT_NOT_EXIST);
		}
		ArrayList<FriendInvitationResponse> res = friendInvitationService.loadInvitations(from);
		return ResultTool.success(res);
	}
	
	@GetMapping(path = "/api/invite/reject")
	public Result<Integer> rejectInvitation(@RequestBody FriendInvitationRequest request, Principal principal) {
		FriendInvitation invitation = null;
		try {
			User from = userService.getUserWithName(principal.getName());
			User to = userService.getUserWithName(request.getTarget());
			invitation = friendInvitationService.getInvitationWithUsers(from, to);
		}catch(Exception e) {
			return ResultTool.fail(ResultCode.INVITATION_INVALID);
		}
		friendInvitationService.rejectInvitation(invitation);
		return ResultTool.success();
	}
	
	@GetMapping(path = "/api/invite/accept")
	public Result<Integer> acceptInvitation(@RequestBody FriendInvitationRequest request, Principal principal) {
		FriendInvitation invitation = null;
		User from = null;
		User to = null;
		try {
			from = userService.getUserWithName(principal.getName());
			to = userService.getUserWithName(request.getTarget());
			invitation = friendInvitationService.getInvitationWithUsers(from, to);
		}catch(Exception e) {
			return ResultTool.fail(ResultCode.INVITATION_INVALID);
		}
		try {
			friendInvitationService.deleteInvitation(invitation);
			friendService.makeFriend(from, to);
			return ResultTool.success();
		}catch(Exception e) {
			return ResultTool.fail(ResultCode.FRIEND_ALREADY);
		}
	}
}
