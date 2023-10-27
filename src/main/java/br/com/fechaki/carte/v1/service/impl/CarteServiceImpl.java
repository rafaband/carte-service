package br.com.fechaki.carte.v1.service.impl;

import br.com.fechaki.carte.exception.type.CarteNotExistException;
import br.com.fechaki.carte.exception.type.CarteNotFoundException;
import br.com.fechaki.carte.exception.type.CarteNotUpdatedException;
import br.com.fechaki.carte.exception.type.NoActivatedCarteException;
import br.com.fechaki.carte.repository.CarteRepository;
import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.service.CarteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
public class CarteServiceImpl implements CarteService {
    private final CarteRepository repository;

    @Override
    public Mono<CarteEntity> create(CarteEntity entity) {
        log.trace("create({})", entity);
        return repository.insert(entity);
    }

    @Override
    public Mono<CarteEntity> read(String idPlace, String idCarte) {
        log.trace("read({}, {})", idPlace, idCarte);
        return repository.findOneByIdPlaceAndIdAndDeletedFalse(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotFoundException(idPlace, idCarte)));
    }

    @Override
    public Mono<CarteEntity> readCurrent(String idPlace) {
        log.trace("readCurrent({})", idPlace);
        return repository.findOneByIdPlace(idPlace)
                .switchIfEmpty(Mono.error(new NoActivatedCarteException(idPlace)));
    }

    @Override
    public Mono<Page<CarteEntity>> readAll(String idPlace, Pageable pageable) {
        log.trace("readAll({}, {})", idPlace, pageable);
        return repository.findAllByIdPlaceAndDeletedFalse(idPlace, pageable)
                .switchIfEmpty(Mono.error(new CarteNotFoundException(idPlace)))
                .collectList()
                .zipWith(repository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    @Override
    public Mono<CarteEntity> update(String idPlace, String idCarte, CarteEntity entity) {
        log.trace("update({}, {}, {})", idPlace, idCarte, entity);
        return repository
                .findOneByIdPlaceAndIdAndDeletedFalse(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .mapNotNull(e -> this.updateEntity(entity, e))
                .switchIfEmpty(Mono.error(new CarteNotUpdatedException(idPlace, idCarte)))
                .flatMap(repository::save);
    }

    @Override
    public Mono<Void> activate(String idPlace, String idCarte) {
        log.trace("activate({}, {}", idPlace, idCarte);
        return repository
                .activateByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }

    @Override
    public Mono<Void> deactivate(String idPlace, String idCarte) {
        log.trace("deactivate({}, {})", idPlace, idCarte);
        return repository
                .deactivateByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }

    @Override
    public Mono<Void> delete(String idPlace, String idCarte) {
        log.trace("delete({}, {})", idPlace, idCarte);
        return repository
                .deleteByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }
}
