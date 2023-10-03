package br.com.fechaki.carte.v1.service;

import br.com.fechaki.carte.repository.CarteRepository;
import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.service.impl.CarteServiceImpl;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CarteServiceTest {
    static final String ID_PLACE = "650af0e13737733c72a88262";
    static final String ID_CARTE = "6512e72c1cad8c536f5b38e9";
    static final String CARTE_TITLE = "Carte Title";
    @Mock
    CarteRepository repository;
    @Mock
    UpdateResult updateResult;
    @InjectMocks
    CarteServiceImpl service;

    CarteEntity createEntity() {
        return CarteEntity.builder()
                .id(ID_CARTE)
                .idPlace(ID_PLACE)
                .title(CARTE_TITLE)
                .startDate(Instant.EPOCH)
                .build();
    }

    CarteEntity createActivatedEntity() {
        return CarteEntity.builder()
                .id(ID_CARTE)
                .idPlace(ID_PLACE)
                .title(String.format(CARTE_TITLE))
                .activated(true)
                .startDate(Instant.EPOCH)
                .build();
    }

    CarteEntity createUpdatedEntity() {
        return CarteEntity.builder()
                .id(ID_CARTE)
                .idPlace(ID_PLACE)
                .title(String.format("%s 1", CARTE_TITLE))
                .activated(true)
                .startDate(Instant.EPOCH)
                .build();
    }

    @Test
    void create() {
        CarteEntity entity = createEntity();

        Mockito.when(repository.insert(entity)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.create(entity))
            .assertNext(item -> {
                assertEquals(ID_CARTE, item.getId());
                assertEquals(ID_PLACE, item.getIdPlace());
                assertEquals(CARTE_TITLE, item.getTitle());
                assertEquals(Instant.EPOCH, item.getStartDate());
            })
        .verifyComplete();
    }

    @Test
    void read() {
        CarteEntity entity = createEntity();

        Mockito.when(repository.findOneByDeletedFalseAndIdPlaceAndId(ID_PLACE, ID_CARTE)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.read(ID_PLACE, ID_CARTE))
                .assertNext(item -> {
                    assertEquals(ID_CARTE, item.getId());
                    assertEquals(ID_PLACE, item.getIdPlace());
                    assertEquals(CARTE_TITLE, item.getTitle());
                    assertEquals(Instant.EPOCH, item.getStartDate());
                })
                .verifyComplete();
    }

    @Test
    void readCurrent() {
        CarteEntity entity = createActivatedEntity();

        Mockito.when(repository.findOneByDeletedFalseAndActivatedTrueAndIdPlace(ID_PLACE)).thenReturn(Mono.just(entity));

        StepVerifier.create(service.readCurrent(ID_PLACE))
                .assertNext(item -> {
                    assertEquals(ID_CARTE, item.getId());
                    assertEquals(ID_PLACE, item.getIdPlace());
                    assertEquals(CARTE_TITLE, item.getTitle());
                    assertEquals(Instant.EPOCH, item.getStartDate());
                })
                .verifyComplete();
    }

    @Test
    void readAll() {
        CarteEntity entity = createActivatedEntity();
        PageRequest pageRequest = PageRequest.of(0, 10);

        Mockito.when(repository.findAllByDeletedFalseAndIdPlace(ID_PLACE, pageRequest)).thenReturn(Flux.just(entity));
        Mockito.when(repository.count()).thenReturn(Mono.just(1L));

        StepVerifier.create(service.readAll(ID_PLACE, pageRequest))
                .assertNext(item -> {
                    assertEquals(1, item.getTotalElements());
                    assertEquals(1, item.getTotalPages());
                })
                .verifyComplete();
    }

    @Test
    void update() {
        CarteEntity entity = createEntity();
        CarteEntity updatedEntity = createUpdatedEntity();

        Mockito.when(repository.findOneByDeletedFalseAndIdPlaceAndId(ID_PLACE, ID_CARTE)).thenReturn(Mono.just(entity));
        Mockito.when(repository.save(any(CarteEntity.class))).thenReturn(Mono.just(updatedEntity));

        StepVerifier.create(service.update(ID_PLACE, ID_CARTE, updatedEntity))
                .assertNext(item -> {
                    assertEquals(ID_CARTE, item.getId());
                    assertEquals(ID_PLACE, item.getIdPlace());
                    assertEquals(String.format("%s 1", CARTE_TITLE), item.getTitle());
                    assertEquals(Instant.EPOCH, item.getStartDate());
                })
                .verifyComplete();
    }

    @Test
    void activate() {
        Mockito.when(repository.activateByIdPlaceAndId(ID_PLACE, ID_CARTE)).thenReturn(Mono.just(updateResult));
        StepVerifier.create(service.activate(ID_PLACE, ID_CARTE)).verifyComplete();
    }

    @Test
    void deactivate() {
        Mockito.when(repository.deactivateByIdPlaceAndId(ID_PLACE, ID_CARTE)).thenReturn(Mono.just(updateResult));
        StepVerifier.create(service.deactivate(ID_PLACE, ID_CARTE)).verifyComplete();
    }

    @Test
    void delete() {
        Mockito.when(repository.deleteByIdPlaceAndId(ID_PLACE, ID_CARTE)).thenReturn(Mono.just(updateResult));
        StepVerifier.create(service.delete(ID_PLACE, ID_CARTE)).verifyComplete();

    }
}