package br.com.fechaki.carte.exception;

import br.com.fechaki.carte.exception.model.ErrorMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ErrorMessage handleNotFound(BaseException exception, ServerRequest serverRequest) {
        exception.setPath(serverRequest.path());
        exception.setRequestId(serverRequest.exchange().getRequest().getId());
        return exception.getErrorMessage();
    }
}
