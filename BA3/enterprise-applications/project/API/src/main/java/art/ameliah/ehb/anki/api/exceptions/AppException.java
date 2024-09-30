package art.ameliah.ehb.anki.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public AppException(String msg) {
        this(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public AppException(String msg, HttpStatus status, Throwable cause) {
        super(msg, cause);
        this.status = status;
    }

    public AppException(String msg, Throwable cause) {
        this(msg, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

}
