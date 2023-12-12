package dev.idan.bgbot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OkEndpoint {

    @GetMapping("/http200")
    public ResponseEntity<String> handle() {
        return new ResponseEntity<>("Http 200", HttpStatus.OK);
    }
}
