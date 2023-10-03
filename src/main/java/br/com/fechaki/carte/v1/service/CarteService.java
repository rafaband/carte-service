package br.com.fechaki.carte.v1.service;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface CarteService {
    Mono<CarteEntity> create(CarteEntity entity);
    Mono<CarteEntity> read(String idPlace, String idCarte);
    Mono<CarteEntity> readCurrent(String idPlace);
    Mono<Page<CarteEntity>> readAll(String idPlace, Pageable pageable);
    Mono<CarteEntity> update(String idPlace, String idCarte, CarteEntity entity);
    Mono<Void> activate(String idPlace, String idCarte);
    Mono<Void> deactivate(String idPlace, String idCarte);
    Mono<Void> delete(String idPlace, String idCarte);

    default CarteEntity updateEntity(CarteEntity pNew, CarteEntity pOld) {
        pNew.setId(pOld.getId());
        if(!pNew.equals(pOld)) {
            BeanUtils.copyProperties(pNew, pOld, "idPlace", "activated", "deleted", "created", "updated");
            pOld.setUpdated(Instant.now());
            return pOld;
        }
        return null;
    }
}
