package com.proyect.instarecipes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {
    @PostMapping("/")
    public String pagetrying1(Model model) {

        return "index";
    }
    @GetMapping("/")
    public String pagetrying2(Model model) {
        return "index";
    }
 
}