package br.com.fechaki.carte.exception;

import br.com.fechaki.carte.exception.model.ErrorMessage;
import br.com.fechaki.carte.exception.model.ErrorMessageType;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {
    private String requestId;
    private String path;
    private ErrorMessageType messageType = ErrorMessageType.WARNING;
    private final Instant timestamp = Instant.now();
    private final Map<String, Object> errors = new HashMap<>();

    protected final void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    protected final void setPath(String path) {
        this.path = path;
    }

    protected final void setMessageType(ErrorMessageType messageType) {
        this.messageType = messageType;
    }

    protected final void addError(String key, Object value) {
        errors.put(key, value);
    }

    public final ErrorMessage getErrorMessage() {
        return ErrorMessage
                .builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .errorType(messageType)
                .messages(errors)
                .build();
    }
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
