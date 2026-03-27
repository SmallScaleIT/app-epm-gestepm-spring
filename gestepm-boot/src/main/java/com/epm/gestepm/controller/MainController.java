package com.epm.gestepm.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() {
		// Just for testing purposes, to be removed later
	}
}
