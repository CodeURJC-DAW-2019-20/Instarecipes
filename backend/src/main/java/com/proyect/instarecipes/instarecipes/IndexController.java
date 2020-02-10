package com.proyect.instarecipes.instarecipes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {
    @RequestMapping("/index")
    public String pagetrying() {

        /*model.addAttribute("name", "World");*/

    return "index";
 }
 
}