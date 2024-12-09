package art.ameliah.ehb.anki.api.annotations;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
@Authenticated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseController {
}
