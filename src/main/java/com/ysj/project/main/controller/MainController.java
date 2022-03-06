package com.ysj.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MainController {
	
	@RequestMapping("/")
	public String getRoot(ModelMap model) {

		return "forward:/main/main";
	}

	@GetMapping("/main/main")
	public String getMain(ModelMap model) {

		return "main/index";
	}
	//Controller
	@GetMapping("/test")
	public @ResponseBody String getTest() {
		return "Service";
	}
	
	
}
