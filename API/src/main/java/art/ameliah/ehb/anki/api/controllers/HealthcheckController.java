package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.AllowAnonymous;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllowAnonymous
@RestController
public class HealthcheckController {

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

}
