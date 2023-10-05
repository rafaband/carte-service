package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.CategoryEntity;
import br.com.fechaki.carte.v1.data.param.CategoryParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {
    static final String CATEGORY_TITLE = "Category Title";
    final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);
    @Test
    @DisplayName("Should convert Category Param to Category Entity")
    void toEntity() {
        CategoryParam param = new CategoryParam(CATEGORY_TITLE, null);

        CategoryEntity entity = mapper.toEntity(param);

        assertEquals(CATEGORY_TITLE, entity.getTitle());
        assertNotNull(entity.getProducts());
        assertEquals(0, entity.getProducts().size());
    }
}