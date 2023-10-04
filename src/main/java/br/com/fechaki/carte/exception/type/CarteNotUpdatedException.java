package br.com.fechaki.carte.exception.type;

import br.com.fechaki.carte.exception.BaseException;

public class CarteNotUpdatedException extends BaseException {
    public CarteNotUpdatedException(String idPlace, String idCarte) {
        super.addError("API", "carte.not.updated");
    }
}
