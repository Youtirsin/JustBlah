package com.youtirsin.blah;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {
	@GetMapping("/test")
	public String getTest() {
		return "Get test success :)";
	}

	@PostMapping("/posttest")
	public String postTest() {
		return "Post test success :)";
	}
}
