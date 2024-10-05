package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.AllowAnonymous;
import art.ameliah.ehb.anki.api.annotations.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllowAnonymous
@BaseController
public class HealthcheckController {

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

}
