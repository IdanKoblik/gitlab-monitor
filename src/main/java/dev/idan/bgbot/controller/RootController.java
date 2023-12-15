package dev.idan.bgbot.controller;

import dev.idan.bgbot.config.ConfigData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path = "/")
public class RootController {

    @GetMapping("/")
    public RedirectView redirectToWebsite() {
        return new RedirectView("https://idankoblik.github.io/gitlab-monitor");
    }
}
