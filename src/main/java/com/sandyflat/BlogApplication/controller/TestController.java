package com.sandyflat.BlogApplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testing")
public class TestController {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public String justTesting(){
        return "successfully";
    }
}
