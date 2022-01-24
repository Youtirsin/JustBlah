package com.youtirsin.blah.friend;

import java.security.Principal;
import java.util.ArrayList;

import com.youtirsin.blah.data.Result;
import com.youtirsin.blah.data.ResultCode;
import com.youtirsin.blah.data.ResultTool;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;


    @GetMapping(path = "/api/friend/isfriend")
    public Result<Boolean> isFriend(@RequestParam(required = true) String friendName, Principal principal) {
        User user1 = null;
        User user2 = null;
        Boolean res = false;
        try {
            user1 = userService.getUserWithName(principal.getName());
            user2 = userService.getUserWithName(friendName);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        try {
            res = friendService.isFriend(user1, user2);
            return ResultTool.success(res);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }

    @GetMapping(path = "/api/friend/makefriend")
    public Result<Integer> makeFriend(@RequestParam(required = true) String friendName, Principal principal) {
        User user1 = null;
        User user2 = null;
        try {
            user1 = userService.getUserWithName(principal.getName());
            user2 = userService.getUserWithName(friendName);
        } catch (Exception e) {
            return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        try {
            friendService.makeFriend(user1, user2);
            return ResultTool.success();
        } catch (Exception e) {
            return ResultTool.fail(ResultCode.FRIEND_ALREADY);
        }
    }

    @GetMapping(path = "/api/friend/load")
    public Result<ArrayList<String>> loadFriends(Principal principal) {
        User user = null;
        try {
            user = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        ArrayList<String> res = friendService.loadFriendNames(user);
        return ResultTool.success(res);
    }

    @GetMapping(path = "/api/friend/delete")
    public Result<Integer> deleteFriend(@RequestParam(required = true) String friendName, Principal principal) {
        User user1 = null;
        User user2 = null;
        try {
            user1 = userService.getUserWithName(principal.getName());
            user2 = userService.getUserWithName(friendName);
            friendService.deleteFriend(user1, user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResultTool.fail();
        }
        return ResultTool.success();
    }
}
