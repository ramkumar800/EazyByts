package com.bmt.Crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}
	@GetMapping("/Salespipeline")
	public String contact() {
		return "Salespipeline";
	}
	

}
