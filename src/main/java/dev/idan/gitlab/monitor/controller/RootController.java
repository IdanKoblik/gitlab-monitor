package dev.idan.gitlab.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path = "/")
public class RootController {

    @GetMapping("/")
    public RedirectView redirectToSecret() {
        String secret = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
        return new RedirectView(secret);
    }
}
