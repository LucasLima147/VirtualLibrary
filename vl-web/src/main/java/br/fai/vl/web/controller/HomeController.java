package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String getHomePage() {
		return "home";
	}

	@GetMapping("/criadores")
	public String getCriadores() {
		return "general/criadores";
	}

	@GetMapping("/not-found")
	public String getNotFound() {
		return "/exception/not-found";
	}
}
