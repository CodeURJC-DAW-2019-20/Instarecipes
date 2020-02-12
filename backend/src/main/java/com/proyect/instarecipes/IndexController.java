package com.proyect.instarecipes;


import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {
    @RequestMapping("/index")
    public String pagetrying() {

        /*model.addAttribute("name", "World");*/

        return "index";
    }

/*
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {
    String titulo;
    String descripcion;

    @PostMapping("/")
    public String pagetrying1(Model model, String titulo, String descripcion) {

        //model.addAttribute("title", titulo);
        //model.addAttribute("description", descripcion);
        this.descripcion = descripcion;
        this.titulo = titulo;

        return "index";
    }
    @GetMapping("/")
    public String pagetrying2(Model model) {

        model.addAttribute("title", titulo);
        model.addAttribute("description", descripcion);

        return "index";
    }
 */
}