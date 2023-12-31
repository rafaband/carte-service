package br.com.fechaki.carte.repository;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarteRepository extends ReactiveMongoRepository<CarteEntity, String>, CarteUpdateRepository {
    Mono<CarteEntity> findOneByIdPlaceAndDeletedFalseAndActivatedTrue(String idPlace);
    Mono<CarteEntity> findOneByIdPlace(String idPlace);

    Flux<CarteEntity> findAllByIdPlaceAndDeletedFalse(String idPlace, Pageable pageable);

    Mono<CarteEntity> findOneByIdPlaceAndIdAndDeletedFalse(String idPlace, String idCarte);
}
