package app.exceptions;

import java.util.List;

public class BadRequestException extends RuntimeException {
    List<String> errorMessages;

    public BadRequestException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
