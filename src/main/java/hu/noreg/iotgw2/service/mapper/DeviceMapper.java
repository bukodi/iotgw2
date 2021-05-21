package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.DeviceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = { KeyPairMapper.class, DeviceTypeMapper.class, OrgUnitMapper.class })
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {
    @Mapping(target = "deviceSignKey", source = "deviceSignKey", qualifiedByName = "keyId")
    @Mapping(target = "deviceEncKey", source = "deviceEncKey", qualifiedByName = "keyId")
    @Mapping(target = "serverSignKey", source = "serverSignKey", qualifiedByName = "keyId")
    @Mapping(target = "serverEncKey", source = "serverEncKey", qualifiedByName = "keyId")
    @Mapping(target = "nextServerSignKey", source = "nextServerSignKey", qualifiedByName = "keyId")
    @Mapping(target = "nextServerEncKey", source = "nextServerEncKey", qualifiedByName = "keyId")
    @Mapping(target = "type", source = "type", qualifiedByName = "name")
    @Mapping(target = "orgUnit", source = "orgUnit", qualifiedByName = "name")
    DeviceDTO toDto(Device s);

    @Named("visualId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "visualId", source = "visualId")
    DeviceDTO toDtoVisualId(Device device);
}
