package br.com.fechaki.carte.repository.impl;


import br.com.fechaki.carte.repository.CarteUpdateRepository;
import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CarteUpdateRepositoryImpl implements CarteUpdateRepository {
    private final ReactiveMongoTemplate template;

    private Query getDefault(String idPlace, String idCarte) {
        return new Query(
            Criteria
                .where("idPlace").is(idPlace)
                .andOperator(Criteria.where("id").is(idCarte))
        );
    }

    @Override
    public Mono<UpdateResult> deleteByIdPlaceAndId(String idPlace, String idCarte) {
        Update update = new Update().set("deleted", true);
        return template.updateFirst(getDefault(idPlace, idCarte), update, CarteEntity.class);
    }

    @Override
    public Mono<UpdateResult> activateByIdPlaceAndId(String idPlace, String idCarte) {
        Update update = new Update().set("activated", true);

        return template
                .updateMulti(new Query(Criteria.where("idPlace").is(idPlace)), new Update().set("activated", false), CarteEntity.class)
                .flatMap(q -> template.updateFirst(getDefault(idPlace, idCarte), update, CarteEntity.class));
    }

    @Override
    public Mono<UpdateResult> deactivateByIdPlaceAndId(String idPlace, String idCarte) {
        Update update = new Update().set("activated", false);
        return template.updateFirst(getDefault(idPlace, idCarte), update, CarteEntity.class);
    }
}
