package br.com.fechaki.carte.v1.handler;

import br.com.fechaki.carte.exception.BaseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidatorHandler {
    private final Validator validator;

    public <T> void validate(T o) {
        Set<ConstraintViolation<T>> validate = validator.validate(o);

        if(!validate.isEmpty()) {
            BaseException exception = new BaseException();

            for(final var violation : validate) {
                String field = violation.getPropertyPath().toString();
                String message = violation.getMessage();

                exception.addError(field, message);
            }

            throw exception;
        }
    }
}
