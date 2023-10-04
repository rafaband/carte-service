package br.com.fechaki.carte.v1.service.impl;

import br.com.fechaki.carte.exception.type.CarteNotExistException;
import br.com.fechaki.carte.exception.type.CarteNotFoundException;
import br.com.fechaki.carte.exception.type.CarteNotUpdatedException;
import br.com.fechaki.carte.exception.type.NoActivatedCarteException;
import br.com.fechaki.carte.repository.CarteRepository;
import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.service.CarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CarteServiceImpl implements CarteService {
    private CarteRepository repository;

    @Override
    public Mono<CarteEntity> create(CarteEntity entity) {
        return repository.insert(entity);
    }

    @Override
    public Mono<CarteEntity> read(String idPlace, String idCarte) {
        return repository.findOneByDeletedFalseAndIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotFoundException(idPlace, idCarte)));
    }

    @Override
    public Mono<CarteEntity> readCurrent(String idPlace) {
        return repository.findOneByDeletedFalseAndActivatedTrueAndIdPlace(idPlace)
                .switchIfEmpty(Mono.error(new NoActivatedCarteException(idPlace)));
    }

    @Override
    public Mono<Page<CarteEntity>> readAll(String idPlace, Pageable pageable) {
        return repository.findAllByDeletedFalseAndIdPlace(idPlace, pageable)
                .switchIfEmpty(Mono.error(new CarteNotFoundException(idPlace)))
                .collectList()
                .zipWith(repository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    @Override
    public Mono<CarteEntity> update(String idPlace, String idCarte, CarteEntity entity) {
        return repository
                .findOneByDeletedFalseAndIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .mapNotNull(e -> this.updateEntity(entity, e))
                .switchIfEmpty(Mono.error(new CarteNotUpdatedException(idPlace, idCarte)))
                .flatMap(repository::save);
    }

    @Override
    public Mono<Void> activate(String idPlace, String idCarte) {
        return repository
                .activateByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }

    @Override
    public Mono<Void> deactivate(String idPlace, String idCarte) {
        return repository
                .deactivateByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }

    @Override
    public Mono<Void> delete(String idPlace, String idCarte) {
        return repository
                .deleteByIdPlaceAndId(idPlace, idCarte)
                .switchIfEmpty(Mono.error(new CarteNotExistException(idPlace, idCarte)))
                .then();
    }
}
