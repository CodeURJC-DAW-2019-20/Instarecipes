package com.proyect.instarecipes.api;

import java.util.Collection;
import java.util.Optional;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.CategoriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController{
   @Autowired
   private CategoriesRepository categoriesRepository;
   @GetMapping("/")
   public Collection<Category> recipes(){
       return categoriesRepository.findAll();
   }
   @GetMapping("/{id}")
   public ResponseEntity<Category> getItem(@PathVariable long id){
       Optional<Category> category = categoriesRepository.findById(id);
       if (category.get() != null){
           return new ResponseEntity<>(category.get(), HttpStatus.OK);
       } else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }
}