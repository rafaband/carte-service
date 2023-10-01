package br.com.fechaki.carte.repository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;

public interface CarteUpdateRepository {
    Mono<UpdateResult> deleteByIdPlaceAndId(String idPlace, String idCarte);
    Mono<UpdateResult> activateByIdPlaceAndId(String idPlace, String idCarte);
    Mono<UpdateResult> deactivateByIdPlaceAndId(String idPlace, String idCarte);
}
