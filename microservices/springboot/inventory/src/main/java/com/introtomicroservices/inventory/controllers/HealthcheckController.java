package com.introtomicroservices.inventory.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.inventory.models.ApiMessage;

@RestController
@RequestMapping("/healthz")
public class HealthcheckController {
    @GetMapping
    public ApiMessage checkHealth() {
        return new ApiMessage("OK");
    }
}