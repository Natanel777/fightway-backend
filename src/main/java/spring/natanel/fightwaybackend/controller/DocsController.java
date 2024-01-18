package spring.natanel.fightwaybackend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Hidden
@Controller
@RequestMapping("/docs")
public class DocsController {
    @GetMapping
    public String getDocs() {
        return "redirect:/swagger-ui/index.html#";
    }
}
