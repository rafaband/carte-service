package br.com.fechaki.carte.v1.service.impl;

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
        return repository.findOneByDeletedFalseAndIdPlaceAndId(idPlace, idCarte);
    }

    @Override
    public Mono<CarteEntity> readCurrent(String idPlace) {
        return repository.findOneByDeletedFalseAndActivatedTrueAndIdPlace(idPlace);
    }

    @Override
    public Mono<Page<CarteEntity>> readAll(String idPlace, Pageable pageable) {
        return repository.findAllByDeletedFalseAndIdPlace(idPlace, pageable)
                .collectList()
                .zipWith(repository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    @Override
    public Mono<CarteEntity> update(String idPlace, String idCarte, CarteEntity entity) {
        return repository
                .findOneByDeletedFalseAndIdPlaceAndId(idPlace, idCarte)
                .mapNotNull(e -> this.updateEntity(entity, e))
                .flatMap(repository::save);
    }

    @Override
    public Mono<Void> activate(String idPlace, String idCarte) {
        return repository
                .activateByIdPlaceAndId(idPlace, idCarte)
                .then();
    }

    @Override
    public Mono<Void> deactivate(String idPlace, String idCarte) {
        return repository
                .deactivateByIdPlaceAndId(idPlace, idCarte)
                .then();
    }

    @Override
    public Mono<Void> delete(String idPlace, String idCarte) {
        return repository
                .deleteByIdPlaceAndId(idPlace, idCarte)
                .then();
    }
}
