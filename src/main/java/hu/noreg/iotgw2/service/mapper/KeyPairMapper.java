package hu.noreg.iotgw2.service.mapper;

import hu.noreg.iotgw2.domain.*;
import hu.noreg.iotgw2.service.dto.KeyPairDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KeyPair} and its DTO {@link KeyPairDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KeyPairMapper extends EntityMapper<KeyPairDTO, KeyPair> {
    @Named("keyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "keyId", source = "keyId")
    KeyPairDTO toDtoKeyId(KeyPair keyPair);
}
