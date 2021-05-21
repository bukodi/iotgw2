package hu.noreg.iotgw2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KeyPairTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyPair.class);
        KeyPair keyPair1 = new KeyPair();
        keyPair1.setId(1L);
        KeyPair keyPair2 = new KeyPair();
        keyPair2.setId(keyPair1.getId());
        assertThat(keyPair1).isEqualTo(keyPair2);
        keyPair2.setId(2L);
        assertThat(keyPair1).isNotEqualTo(keyPair2);
        keyPair1.setId(null);
        assertThat(keyPair1).isNotEqualTo(keyPair2);
    }
}
