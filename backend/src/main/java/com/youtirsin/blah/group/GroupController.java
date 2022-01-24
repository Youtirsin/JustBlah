package com.youtirsin.blah.group;

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
public class GroupController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @GetMapping(path = "/api/group/create")
    public Result<Integer> createGroup(@RequestParam(required = true) String groupName, Principal principal) {
        User founder = null;
        try {
          founder = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        }
        try {
            groupService.createGroup(founder, groupName);
            groupService.joinGroup(founder, groupName);
            return ResultTool.success();
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }
    
    @GetMapping(path = "/api/group/isingroup")
    public Result<Boolean> isFriend(@RequestParam(required = true) String groupName, Principal principal) {
        User member = null;
        Boolean res = false;
        try {
            member = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
        }

        try {
            res = groupService.isInGroup(member, groupName);
            return ResultTool.success(res);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }

    @GetMapping(path = "/api/group/getinfo")
    public Result<GroupResponse> getGroupInfo(@RequestParam(required = true) String groupName) {
        GroupResponse response = groupService.getGroupInfo(groupName);
        return ResultTool.success(response);
    }

    @GetMapping(path = "/api/group/load")
    public Result<ArrayList<String>> loadGroups(Principal principal) {
        User user = null;
        try {
          user = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
        }
        try {
          ArrayList<String> res = groupService.loadGroups(user);
          return ResultTool.success(res);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }

    @GetMapping(path = "/api/group/search")
    public Result<ArrayList<String>> searchGroup(@RequestParam(required = true, defaultValue = "") String keyword, Principal principal) {
        try {
          ArrayList<String> res = groupService.searchGroup(keyword);
          return ResultTool.success(res);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }

    @GetMapping(path = "/api/group/join")
    public Result<Integer> joinGroup(@RequestParam(required = true) String groupName, Principal principal) {
        User founder = null;
        try {
          founder = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        }
        try {
            groupService.joinGroup(founder, groupName);
            return ResultTool.success();
        } catch (Exception e) {
          System.out.println(e.getMessage());
          return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }

    @GetMapping(path = "/api/group/quit")
    public Result<Integer> quitGroup(@RequestParam(required = true) String groupName, Principal principal) {
        User founder = null;
        try {
          founder = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        }
        try {
            groupService.quitGroup(founder, groupName);
            return ResultTool.success();
        } catch (Exception e) {
          System.out.println(e.getMessage());
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
    }
}
