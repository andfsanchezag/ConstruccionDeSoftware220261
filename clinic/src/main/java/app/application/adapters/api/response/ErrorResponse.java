package app.application.adapters.api.response;

import java.util.List;

public record ErrorResponse(int status, String message, List<String> errors) {

    public ErrorResponse(int status, String message) {
        this(status, message, null);
    }
}
