package com.youtirsin.blah.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtirsin.blah.data.Result;
import com.youtirsin.blah.data.ResultCode;
import com.youtirsin.blah.data.ResultTool;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserService;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/api/user/register")
	public Result<Integer> register(@RequestBody RegistrationRequest request) {
		try {
			userService.signUp(new User(
				request.getName(),
				request.getPassword(),
				request.getEmail()
				));
			return ResultTool.success();
		} catch(Exception e) {
			return ResultTool.fail(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
		} 
	}
	
	@PostMapping(path = "/api/user/reset")
	public Result<Integer> reset(@RequestBody RegistrationRequest request) {
		User user = null;
		try {
			user = userService.getUserWithName(request.getName());
		}catch (Exception e) {
			return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
		}
		if (!user.getEmail().equals(request.getEmail())) {
			return ResultTool.fail(ResultCode.USER_RESET_VERIFIY_FAIL);
		}
		try {
			userService.reset(user);
			return ResultTool.success();
		}catch (Exception e) {
			return ResultTool.fail();
		}
	}
}
