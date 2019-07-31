package com.neeraj.jenkins.cicdwithjenkins.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neeraj on 2019-07-12
 * Copyright (c) 2019, ci-cd-with-jenkins.
 * All rights reserved.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World from Jenkins via feature branch, okay no more fun!";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Heart Beat running fine :)";
    }
}
