package org.example.cas_example1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

        @GetMapping
        public String index() {
            return "Hello Welcome to Cas_Example2 !!";
        }
}
