package com.taewan.inflearnSpringRESTAPI.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResourceSupport index() {

    }
}
