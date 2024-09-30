package art.ameliah.ehb.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping("hello")
    public String greeting() {
        return "Hello, World!";
    }

    @PostMapping("form")
    public String helloName(@RequestParam(defaultValue = "Daan Banaan") String name) {
        return "Hello, " + name + "!";
    }

}
