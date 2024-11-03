package io.security.cors1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Cors1Controller {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
