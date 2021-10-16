package org.cs3219.project.peerprep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class HealthController {
    @GetMapping
    public String helloWorldAtRoot() {
        return "Hello World at Root!";
    }

    @GetMapping(path = "hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
