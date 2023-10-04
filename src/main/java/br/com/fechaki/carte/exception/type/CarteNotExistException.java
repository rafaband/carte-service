package br.com.fechaki.carte.exception.type;

import br.com.fechaki.carte.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CarteNotExistException extends BaseException {
    public CarteNotExistException(String idPlace, String idCarte) {
        super.addError("API", "carte.not.exist");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
