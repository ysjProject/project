package com.ysj.project.web.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class TestController {

	@RequestMapping("/")
	public String home() {
		
		return "/common/home";
	}
	
	@RequestMapping(value="record",method=RequestMethod.GET)
	public String record() {
		
		return "/common/record";
	}
	
	@RequestMapping(value="ref",method=RequestMethod.GET)
	public String ref() {
		
		return "/common/ref";
	}
	
	@RequestMapping(value="word", method=RequestMethod.GET)
	public String word() {
		
		return "/common/word";
	}
	
	@RequestMapping(value="loginPage", method=RequestMethod.GET)
	public String login() {
		
		return "/common/login";
	}
	
	@RequestMapping(value="joinPage", method=RequestMethod.GET)
	public String join() {
		
		return "/common/join";
	}
	
	@RequestMapping(value="resume", method=RequestMethod.GET)
	public String resume() {
		
		return "/common/resume";
	}
	
	@RequestMapping(value="ability", method=RequestMethod.GET)
	public String ability() {
		
		return "/common/ability";
	}
	
	@RequestMapping(value="certificate", method=RequestMethod.GET)
	public String certificate() {
		
		return "/common/certificate";
	}
	
	@RequestMapping(value="self", method=RequestMethod.GET)
	public String self() {
		
		return "/common/self";
	}
	
}
