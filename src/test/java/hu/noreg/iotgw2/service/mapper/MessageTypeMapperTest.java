package hu.noreg.iotgw2.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageTypeMapperTest {

    private MessageTypeMapper messageTypeMapper;

    @BeforeEach
    public void setUp() {
        messageTypeMapper = new MessageTypeMapperImpl();
    }
}
