package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.OrgUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrgUnit} and its DTO {@link OrgUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrgUnitMapper extends EntityMapper<OrgUnitDTO, OrgUnit> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "name")
    OrgUnitDTO toDto(OrgUnit s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrgUnitDTO toDtoName(OrgUnit orgUnit);
}
