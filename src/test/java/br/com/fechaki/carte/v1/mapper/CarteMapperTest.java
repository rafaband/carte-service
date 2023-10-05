package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.data.param.CarteParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class CarteMapperTest {
    static final String ID_PLACE = "650af0e13737733c72a88262";
    static final String CARTE_TITLE = "Carte Title";
    final CarteMapper mapper = Mappers.getMapper(CarteMapper.class);

    @Test
    @DisplayName("Should convert Carte Param to Carte Entity")
    void toEntity() {
        CarteParam carteParam = new CarteParam(
                ID_PLACE,
                CARTE_TITLE,
                LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC),
                null,
                null);

        CarteEntity entity = mapper.toEntity(carteParam);

        assertEquals(ID_PLACE, entity.getIdPlace());
        assertEquals(CARTE_TITLE, entity.getTitle());
        assertEquals(Instant.EPOCH, entity.getStartDate());
        assertNull(entity.getEndDate());
        assertFalse(entity.isActivated());
        assertNotNull(entity.getCategories());
        assertEquals(0, entity.getCategories().size());
    }
}