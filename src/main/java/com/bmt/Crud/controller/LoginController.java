package com.bmt.Crud.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	

		
		
//		custom logouts made to get out of session instead default logout
		
	@GetMapping("/logout")
	public String handleLogoutRequest(HttpServletRequest requests) {
		
		requests.getSession().invalidate();
		return "redirect:/login";
		}
		
		@GetMapping("/login")
		public String showLoginForm(Model model) {
			
			Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
			if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
				return "login";
			}
			return "redirect:/";
			
		}
		
	}

