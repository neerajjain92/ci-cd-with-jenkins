package com.neeraj.jenkins.cicdwithjenkins.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neeraj on 2019-07-31
 * Copyright (c) 2019, ci-cd-with-jenkins.
 * All rights reserved.
 */
//@RestController
@RequestMapping("/version")
public class GreenController {

    @GetMapping
    public String getMapping() {
        return "Green App v0.1";
    }
}
