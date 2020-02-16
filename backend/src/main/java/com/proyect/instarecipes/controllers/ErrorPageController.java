package com.proyect.instarecipes.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController  {
 
    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "error";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }

    // @RequestMapping("/error")
    // public String handleError(HttpServletRequest request) {
    //     Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
    //     if (status != null) {
    //         Integer statusCode = Integer.valueOf(status.toString());
        
    //         if(statusCode == HttpStatus.NOT_FOUND.value()) {
    //             return "error-404";
    //         }
    //         else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
    //             return "error-500";
    //         }
    //     }
    //     return "error";
    // }
}