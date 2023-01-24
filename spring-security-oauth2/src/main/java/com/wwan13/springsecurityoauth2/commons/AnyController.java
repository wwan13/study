package com.wwan13.springsecurityoauth2.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnyController {

    @GetMapping(value = "/api/test/any")
    public ResponseEntity getRequest() {
        String string = "asd";

        return ResponseEntity.ok().body(string);
    }

}
