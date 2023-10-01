package br.com.fechaki.carte.repository;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Testcontainers
class CarteUpdateRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private CarteRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll().block();
    }
    @Test
    @DisplayName("Should Delete Carte by Given Place and ID")
    void shouldFlagDeletedCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.deleteByIdPlaceAndId(result.getIdPlace(), result.getId()))
                .assertNext(item -> assertEquals(1, item.getModifiedCount()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should Activate Carte by Given Place and ID")
    void shouldActivateCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.activateByIdPlaceAndId(result.getIdPlace(), result.getId()))
                .assertNext(item -> assertEquals(1, item.getModifiedCount()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should deactivate Carte by Given Place and ID")
    void shouldDeactivateCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .activated(true)
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.deactivateByIdPlaceAndId(result.getIdPlace(), result.getId()))
                .assertNext(item -> assertEquals(1, item.getModifiedCount()))
                .verifyComplete();
    }

}