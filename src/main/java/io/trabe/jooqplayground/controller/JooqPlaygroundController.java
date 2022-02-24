package io.trabe.jooqplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test", produces = "application/json")
public class JooqPlaygroundController {

    @GetMapping
    public String hi() {
        return "hi";
    }
}
