package br.com.fechaki.carte.v1.mapper;

import br.com.fechaki.carte.v1.data.entity.CarteEntity;
import br.com.fechaki.carte.v1.data.model.Carte;
import br.com.fechaki.carte.v1.data.param.CarteParam;
import br.com.fechaki.carte.v1.mapper.converter.DateConverterMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    uses = { DateConverterMapper.class, CategoryMapper.class },
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface CarteMapper {
    CarteEntity toEntity(CarteParam param);
    Carte toModel(CarteEntity entity);
}
