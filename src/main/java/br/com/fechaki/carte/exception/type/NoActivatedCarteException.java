package br.com.fechaki.carte.exception.type;

import br.com.fechaki.carte.exception.BaseException;
import br.com.fechaki.carte.exception.model.ErrorMessageType;
import org.springframework.http.HttpStatus;

public class NoActivatedCarteException extends BaseException {
    public NoActivatedCarteException(String idPlace) {
        super.addError("API", "carte.not.activated");
        super.setMessageType(ErrorMessageType.ERROR);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
