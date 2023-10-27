package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.ProductEntity;
import br.com.fechaki.carte.v1.data.model.Product;
import br.com.fechaki.carte.v1.data.param.ProductParam;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ProductMapper {
    ProductEntity toEntity(ProductParam param);
    Product toModel(ProductEntity entity);
}