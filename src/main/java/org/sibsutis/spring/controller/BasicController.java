package org.sibsutis.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {
    @GetMapping("/author")
    public String author() {
        return "lab3/author";
    }

    @GetMapping()
    public String index() {
        return "lab3/index";
    }
}
