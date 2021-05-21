package hu.noreg.iotgw2.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessorMapperTest {

    private ProcessorMapper processorMapper;

    @BeforeEach
    public void setUp() {
        processorMapper = new ProcessorMapperImpl();
    }
}
