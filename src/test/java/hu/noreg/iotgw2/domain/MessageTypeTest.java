package hu.noreg.iotgw2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageType.class);
        MessageType messageType1 = new MessageType();
        messageType1.setId(1L);
        MessageType messageType2 = new MessageType();
        messageType2.setId(messageType1.getId());
        assertThat(messageType1).isEqualTo(messageType2);
        messageType2.setId(2L);
        assertThat(messageType1).isNotEqualTo(messageType2);
        messageType1.setId(null);
        assertThat(messageType1).isNotEqualTo(messageType2);
    }
}
