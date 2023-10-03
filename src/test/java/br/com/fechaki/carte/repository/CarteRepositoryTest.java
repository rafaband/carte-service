package br.com.fechaki.carte.repository;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class CarteRepositoryTest {
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
    @DisplayName("Should save a new Carte")
    void shouldSaveCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();

        StepVerifier.create(repository.insert(entity))
                .assertNext(item -> {
                    assertNotNull(item.getId());
                    assertEquals("Carte for Test", item.getTitle());
                    assertEquals("4f7750bd-ca43-4c2d-960a-b59436c78574", item.getIdPlace());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should find Carte by Given ID")
    void shouldFindCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.findById(result.getId()))
                .assertNext(item -> {
                    assertNotNull(item.getId());
                    assertEquals("Carte for Test", item.getTitle());
                    assertEquals("4f7750bd-ca43-4c2d-960a-b59436c78574", item.getIdPlace());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should find activated and not deleted Carte by Given Place")
    void shouldFindCurrentCarte() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .activated(true)
                .deleted(false)
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.findOneByDeletedFalseAndActivatedTrueAndIdPlace(result.getIdPlace()))
                .assertNext(item -> {
                    assertNotNull(item.getId());
                    assertEquals("Carte for Test", item.getTitle());
                    assertEquals("4f7750bd-ca43-4c2d-960a-b59436c78574", item.getIdPlace());
                    assertTrue(item.isActivated());
                    assertFalse(item.isDeleted());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should find all activated Cartes by Given Place")
    void shouldFindCartesByPlace() {
        final String ID_PLACE = "4f7750bd-ca43-4c2d-960a-b59436c78574";
        CarteEntity entity1 = CarteEntity
                .builder()
                .idPlace(ID_PLACE)
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result1 = repository.insert(entity1).block();

        CarteEntity entity2 = CarteEntity
                .builder()
                .idPlace(ID_PLACE)
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result2 = repository.insert(entity2).block();

        PageRequest pageable = PageRequest.of(0, 10);

        assert result1 != null;
        assert result2 != null;
        StepVerifier.create(repository.findAllByDeletedFalseAndIdPlace(ID_PLACE, pageable))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should find Carte by Given ID and Place")
    void shouldFindCarteByIdAndPlace() {
        CarteEntity entity = CarteEntity
                .builder()
                .idPlace("4f7750bd-ca43-4c2d-960a-b59436c78574")
                .title("Carte for Test")
                .startDate(Instant.now())
                .build();
        CarteEntity result = repository.insert(entity).block();

        assert result != null;
        StepVerifier.create(repository.findOneByDeletedFalseAndIdPlaceAndId(result.getIdPlace(), result.getId()))
                .assertNext(item -> {
                    assertNotNull(item.getId());
                    assertEquals("Carte for Test", item.getTitle());
                    assertEquals("4f7750bd-ca43-4c2d-960a-b59436c78574", item.getIdPlace());
                })
                .verifyComplete();
    }
}