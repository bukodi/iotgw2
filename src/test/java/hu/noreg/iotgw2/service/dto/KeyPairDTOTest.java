package hu.noreg.iotgw2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KeyPairDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyPairDTO.class);
        KeyPairDTO keyPairDTO1 = new KeyPairDTO();
        keyPairDTO1.setId(1L);
        KeyPairDTO keyPairDTO2 = new KeyPairDTO();
        assertThat(keyPairDTO1).isNotEqualTo(keyPairDTO2);
        keyPairDTO2.setId(keyPairDTO1.getId());
        assertThat(keyPairDTO1).isEqualTo(keyPairDTO2);
        keyPairDTO2.setId(2L);
        assertThat(keyPairDTO1).isNotEqualTo(keyPairDTO2);
        keyPairDTO1.setId(null);
        assertThat(keyPairDTO1).isNotEqualTo(keyPairDTO2);
    }
}
