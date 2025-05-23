package judamov.sipoh.exceptions;

import org.springframework.http.HttpStatus;

public class GenericAppException extends AppException {
    public GenericAppException(HttpStatus status, String message) {
        super(status, message);
    }
}
