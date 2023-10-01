package br.com.fechaki.carte.repository;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarteRepository extends ReactiveMongoRepository<CarteEntity, String>, CarteUpdateRepository {
    Mono<CarteEntity> findOneByDeletedFalseAndActivatedTrueAndIdPlace(String idPlace);

    Flux<CarteEntity> findAllByDeletedFalseAndIdPlace(String idPlace);

    Mono<CarteEntity> findOneByDeletedFalseAndIdPlaceAndId(String idPlace, String idCarte);
}
