package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.MessageTypeDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageType} and its DTO {@link MessageTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessorMapper.class })
public interface MessageTypeMapper extends EntityMapper<MessageTypeDTO, MessageType> {
    @Mapping(target = "messageProcessor", source = "messageProcessor", qualifiedByName = "name")
    @Mapping(target = "timeoutProcessor", source = "timeoutProcessor", qualifiedByName = "name")
    MessageTypeDTO toDto(MessageType s);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<MessageTypeDTO> toDtoNameSet(Set<MessageType> messageType);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MessageTypeDTO toDtoName(MessageType messageType);
}
