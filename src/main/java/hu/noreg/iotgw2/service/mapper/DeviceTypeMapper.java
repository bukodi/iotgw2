package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.DeviceTypeDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceType} and its DTO {@link DeviceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessorMapper.class, MessageTypeMapper.class })
public interface DeviceTypeMapper extends EntityMapper<DeviceTypeDTO, DeviceType> {
    @Mapping(target = "enrollProcessor", source = "enrollProcessor", qualifiedByName = "name")
    @Mapping(target = "messageTypes", source = "messageTypes", qualifiedByName = "nameSet")
    DeviceTypeDTO toDto(DeviceType s);

    @Mapping(target = "removeMessageTypes", ignore = true)
    DeviceType toEntity(DeviceTypeDTO deviceTypeDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DeviceTypeDTO toDtoName(DeviceType deviceType);
}
