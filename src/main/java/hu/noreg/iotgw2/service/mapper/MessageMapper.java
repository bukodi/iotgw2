package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring", uses = { MessageTypeMapper.class, DeviceMapper.class })
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "type", source = "type", qualifiedByName = "name")
    @Mapping(target = "device", source = "device", qualifiedByName = "visualId")
    MessageDTO toDto(Message s);
}
