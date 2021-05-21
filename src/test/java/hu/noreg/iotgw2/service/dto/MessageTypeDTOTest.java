package hu.noreg.iotgw2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageTypeDTO.class);
        MessageTypeDTO messageTypeDTO1 = new MessageTypeDTO();
        messageTypeDTO1.setId(1L);
        MessageTypeDTO messageTypeDTO2 = new MessageTypeDTO();
        assertThat(messageTypeDTO1).isNotEqualTo(messageTypeDTO2);
        messageTypeDTO2.setId(messageTypeDTO1.getId());
        assertThat(messageTypeDTO1).isEqualTo(messageTypeDTO2);
        messageTypeDTO2.setId(2L);
        assertThat(messageTypeDTO1).isNotEqualTo(messageTypeDTO2);
        messageTypeDTO1.setId(null);
        assertThat(messageTypeDTO1).isNotEqualTo(messageTypeDTO2);
    }
}
