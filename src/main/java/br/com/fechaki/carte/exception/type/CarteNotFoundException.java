package br.com.fechaki.carte.exception.type;

import br.com.fechaki.carte.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class CarteNotFoundException extends BaseException {
    public CarteNotFoundException(String idPlace) {
        this(idPlace, null);
    }
    public CarteNotFoundException(String idPlace, String idCarte) {
        super.addError("API", "carte.not.found");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
