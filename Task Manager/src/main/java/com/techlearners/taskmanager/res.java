package com.techlearners.taskmanager;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/h")
public class res {

    @GetMapping()
    public String he(){
        return "qa";
    }
}
