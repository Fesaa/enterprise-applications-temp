package art.ameliah.ehb.anki.api.controllers.advice;

import art.ameliah.ehb.anki.api.exceptions.AppException;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@RestControllerAdvice
public class ExceptionTranslator {

    private static final String LOG_FORMAT = "[%s] Caught unhandled exception %s: %s";

    private final Map<Class<?>, String> exceptionCodeMap = new HashMap<>();
    private final Random random = new Random();

    public ExceptionTranslator() {
        exceptionCodeMap.put(SQLException.class, "DB%s");
        exceptionCodeMap.put(AppException.class, "APP%s");
    }

    private String generateCode(Class<?> exceptionClass) {
        String code = exceptionCodeMap.get(exceptionClass);
        if (code == null) {
            return "ERR" + random.nextInt(100000);
        }
        return String.format(code, random.nextInt(100000));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDatabaseException(SQLException e) {
        String code = generateCode(e.getClass());
        log.error(LOG_FORMAT.formatted(code, e.getClass(), e.getMessage()), e);
        return ResponseEntity.internalServerError().body(code);
    }

    @ExceptionHandler(value = { AppException.class })
    public ResponseEntity<?> handleGenericException(AppException e) {
        String code = generateCode(e.getClass());
        log.error(LOG_FORMAT.formatted(code, e.getClass(), e.getMessage()), e);
        return ResponseEntity.internalServerError().body(code);
    }

    @ExceptionHandler(value = { NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = { UnAuthorized.class})
    public ResponseEntity<?> handleUnAuthorized(UnAuthorized e) {
        return ResponseEntity.status(401).build();
    }


}
