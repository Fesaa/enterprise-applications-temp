package art.ameliah.ehb.anki.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


@Slf4j
@Controller
public class RedirectController {

    @RequestMapping("/{path:[^\\.]*}")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }
}

