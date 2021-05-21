package hu.noreg.iotgw2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processor.class);
        Processor processor1 = new Processor();
        processor1.setId(1L);
        Processor processor2 = new Processor();
        processor2.setId(processor1.getId());
        assertThat(processor1).isEqualTo(processor2);
        processor2.setId(2L);
        assertThat(processor1).isNotEqualTo(processor2);
        processor1.setId(null);
        assertThat(processor1).isNotEqualTo(processor2);
    }
}
