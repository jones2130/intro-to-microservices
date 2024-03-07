package com.introtomicroservices.inventory.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.introtomicroservices.shared.models.ApiMessage;

/**
 * @author jjones
 */
@RestController
@RequestMapping("/healthz")
public class HealthcheckController {
    /**
     * Returns an API message showing that the server is active
     * @return the API message
     */
    @GetMapping
    public ApiMessage checkHealth() {
        return new ApiMessage("OK");
    }
}