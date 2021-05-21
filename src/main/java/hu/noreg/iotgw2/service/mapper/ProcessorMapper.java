package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.ProcessorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Processor} and its DTO {@link ProcessorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessorMapper extends EntityMapper<ProcessorDTO, Processor> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProcessorDTO toDtoName(Processor processor);
}
