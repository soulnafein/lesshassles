package com.lesshassles.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/session/*.htm")
public class SessionController {
	
	@RequestMapping(value = "new.htm")
	public String showLogin() {
		return "sessionNew";
	}

}
