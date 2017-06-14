package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@Autowired 
	UserRepository userRepo;
	
    @RequestMapping("/")
    public String home(Model model) {
    	System.out.println(userRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "index";
    }

}
