package com.youtirsin.blah.user;

import java.security.Principal;
import java.util.ArrayList;

import com.youtirsin.blah.data.Result;
import com.youtirsin.blah.data.ResultCode;
import com.youtirsin.blah.data.ResultTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/api/user/info")
    public Result<UserInfo> getUserInfo(@RequestParam(required = true) String name) {
        User user = null;
        try {
            user = userService.getUserWithName(name);
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
        UserInfo res = new UserInfo(user.getName(), user.getEmail(), user.getDescription());
        return ResultTool.success(res);
    }

    @GetMapping(path = "/api/user/whoami")
    public Result<String> getMyName(Principal principal) {
        try {
            return ResultTool.success(principal.getName());
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.USER_NOT_LOGIN);
        }
    }

    @GetMapping(path = "/api/user/searchuser")
    public Result<ArrayList<String>> searchUser(@RequestParam(required = true, defaultValue = "") String keyword) {
        ArrayList<String> res = userService.searchUsers(keyword);
        return ResultTool.success(res);
    }
    
    @GetMapping(path = "/api/user/myinfo")
    public Result<UserInfo> getMyInfo(Principal principal) {
        User user = null;
        try {
            user = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.dataFail(ResultCode.COMMON_FAIL);
        }
        UserInfo res = new UserInfo(user.getName(), user.getEmail(), user.getDescription());
        return ResultTool.success(res);
    }

    @PostMapping(path = "/api/user/updatemyinfo")
    public Result<Integer> updateMyInfo(@RequestBody(required = true) UserInfo info, Principal principal) {
        if (!principal.getName().equals(info.getName())) {
            System.out.println("not valid name");
            return ResultTool.fail();
        }
        User user = null;
        try {
            user = userService.getUserWithName(principal.getName());
        } catch (Exception e) {
            return ResultTool.fail();
        }
        user.setDescription(info.getDescription());
        userService.updateUser(user);
        return ResultTool.success();
    }
}
