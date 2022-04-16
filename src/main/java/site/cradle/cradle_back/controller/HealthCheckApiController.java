package site.cradle.cradle_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {

    @GetMapping("/healthCheck")
    public String health() {
        return "ok";
    }
}

