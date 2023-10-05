package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.CategoryEntity;
import br.com.fechaki.carte.v1.data.param.CategoryParam;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    uses = { ProductMapper.class },
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface CategoryMapper {
    CategoryEntity toEntity(CategoryParam param);
}
