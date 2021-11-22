package com.youtirsin.blah.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	@PostMapping(path = "/api/user/register")
	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}
}
