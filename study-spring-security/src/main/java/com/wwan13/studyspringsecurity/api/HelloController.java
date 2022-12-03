package com.wwan13.studyspringsecurity.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/apis")
public class HelloController {

    @GetMapping(value = "/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok().body("hello");
    }

}
