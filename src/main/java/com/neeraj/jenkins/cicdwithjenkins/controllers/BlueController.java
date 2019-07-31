package com.neeraj.jenkins.cicdwithjenkins.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller will be used to showcase How Blue-Green Deployment works
 *
 * @author neeraj on 2019-07-31
 * Copyright (c) 2019, ci-cd-with-jenkins.
 * All rights reserved.
 */
@RestController
@RequestMapping("/version")
public class BlueController {

    @GetMapping
    public String getMapping() {
        return "Blue App v0.1";
    }
}
