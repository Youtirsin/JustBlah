package com.youtirsin.blah;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {
	@GetMapping("/test")
	public String greeting() {
		return "okay";
	}
}
