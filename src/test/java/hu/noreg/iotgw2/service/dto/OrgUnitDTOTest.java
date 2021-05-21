package hu.noreg.iotgw2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import hu.noreg.iotgw2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgUnitDTO.class);
        OrgUnitDTO orgUnitDTO1 = new OrgUnitDTO();
        orgUnitDTO1.setId(1L);
        OrgUnitDTO orgUnitDTO2 = new OrgUnitDTO();
        assertThat(orgUnitDTO1).isNotEqualTo(orgUnitDTO2);
        orgUnitDTO2.setId(orgUnitDTO1.getId());
        assertThat(orgUnitDTO1).isEqualTo(orgUnitDTO2);
        orgUnitDTO2.setId(2L);
        assertThat(orgUnitDTO1).isNotEqualTo(orgUnitDTO2);
        orgUnitDTO1.setId(null);
        assertThat(orgUnitDTO1).isNotEqualTo(orgUnitDTO2);
    }
}
