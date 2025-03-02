package com.example.webfilter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/permit-all")
	public String permitAll() {
		return "Ok";
	}

	@GetMapping("/member-only")
	public String memberOnly() {
		return "Welcome";
	}

	@GetMapping("/token-only")
	public String tokenOnly() {
		return "Token~";
	}

	@GetMapping("/token-member-only")
	public String tokenMemberOnly() {
		return "TokenAndMember~";
	}

}
