package com.familywebshop.stylet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path = "/")
    public String loadMainPage(){
        return "This is the main page.";
    }
    @GetMapping(path = "/user")
    public String getUserPage(){
        return "users page";
    }

    @GetMapping(path = "/admin")
    public String getAdminPage(){
        return "admins page";
    }
}
