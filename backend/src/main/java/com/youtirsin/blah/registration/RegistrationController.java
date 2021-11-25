package com.youtirsin.blah.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.user.User;
import com.youtirsin.blah.user.UserService;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/api/user/register")
	public MessageResult register(@RequestBody RegistrationRequest request) {
		return userService.signUp(new User(
				request.getName(),
				request.getPassword(),
				request.getEmail()
				));
	}
	
	@PostMapping(path = "/api/user/reset")
	public MessageResult reset(@RequestBody RegistrationRequest request) {
		return userService.reset(new User(
				request.getName(),
				request.getPassword(),
				request.getEmail()
				));
	}
}
