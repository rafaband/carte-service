package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.ProductEntity;
import br.com.fechaki.carte.v1.data.param.ProductParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    static final String PRODUCT_CODE = "PC001";
    static final String PRODUCT_TITLE = "Product Title";
    static final String PRODUCT_DESCRIPTION = "Description for Product";
    static final boolean PRODUCT_ACTIVATED = true;
    static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(29.90);

    final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    @DisplayName("Should convert Product Param to Product Entity")
    void toEntity() {
        ProductParam param = new ProductParam(
            PRODUCT_CODE,
            PRODUCT_TITLE,
            PRODUCT_DESCRIPTION,
            PRODUCT_ACTIVATED,
            PRODUCT_PRICE
        );

        ProductEntity entity = mapper.toEntity(param);

        assertEquals(PRODUCT_CODE, entity.getCode());
        assertEquals(PRODUCT_TITLE, entity.getTitle());
        assertEquals(PRODUCT_DESCRIPTION, entity.getDescription());
        assertTrue(entity.isActivated());
        assertEquals(PRODUCT_PRICE, entity.getPrice());
    }
}