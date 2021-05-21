package hu.noreg.iotgw2.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.Device} entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    /**
     * Unique id of the IoT device
     */
    @NotNull
    @ApiModelProperty(value = "Unique id of the IoT device", required = true)
    private String visualId;

    /**
     * Optional description
     */
    @ApiModelProperty(value = "Optional description")
    private String description;

    /**
     * Unique random passphrase for first-time enrollment.
     */
    @ApiModelProperty(value = "Unique random passphrase for first-time enrollment.")
    private String enrollmentCode;

    private ZonedDateTime enrollmentTime;

    private String stateFieldValue01;

    private String stateFieldValue02;

    private String stateFieldValue03;

    private String stateFieldValue04;

    @Lob
    private String lastError;

    private String readAuthPattern;

    private String writeAuthPattern;

    private KeyPairDTO deviceSignKey;

    private KeyPairDTO deviceEncKey;

    private KeyPairDTO serverSignKey;

    private KeyPairDTO serverEncKey;

    private KeyPairDTO nextServerSignKey;

    private KeyPairDTO nextServerEncKey;

    private DeviceTypeDTO type;

    private OrgUnitDTO orgUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisualId() {
        return visualId;
    }

    public void setVisualId(String visualId) {
        this.visualId = visualId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnrollmentCode() {
        return enrollmentCode;
    }

    public void setEnrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    public ZonedDateTime getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(ZonedDateTime enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getStateFieldValue01() {
        return stateFieldValue01;
    }

    public void setStateFieldValue01(String stateFieldValue01) {
        this.stateFieldValue01 = stateFieldValue01;
    }

    public String getStateFieldValue02() {
        return stateFieldValue02;
    }

    public void setStateFieldValue02(String stateFieldValue02) {
        this.stateFieldValue02 = stateFieldValue02;
    }

    public String getStateFieldValue03() {
        return stateFieldValue03;
    }

    public void setStateFieldValue03(String stateFieldValue03) {
        this.stateFieldValue03 = stateFieldValue03;
    }

    public String getStateFieldValue04() {
        return stateFieldValue04;
    }

    public void setStateFieldValue04(String stateFieldValue04) {
        this.stateFieldValue04 = stateFieldValue04;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public String getReadAuthPattern() {
        return readAuthPattern;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return writeAuthPattern;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public KeyPairDTO getDeviceSignKey() {
        return deviceSignKey;
    }

    public void setDeviceSignKey(KeyPairDTO deviceSignKey) {
        this.deviceSignKey = deviceSignKey;
    }

    public KeyPairDTO getDeviceEncKey() {
        return deviceEncKey;
    }

    public void setDeviceEncKey(KeyPairDTO deviceEncKey) {
        this.deviceEncKey = deviceEncKey;
    }

    public KeyPairDTO getServerSignKey() {
        return serverSignKey;
    }

    public void setServerSignKey(KeyPairDTO serverSignKey) {
        this.serverSignKey = serverSignKey;
    }

    public KeyPairDTO getServerEncKey() {
        return serverEncKey;
    }

    public void setServerEncKey(KeyPairDTO serverEncKey) {
        this.serverEncKey = serverEncKey;
    }

    public KeyPairDTO getNextServerSignKey() {
        return nextServerSignKey;
    }

    public void setNextServerSignKey(KeyPairDTO nextServerSignKey) {
        this.nextServerSignKey = nextServerSignKey;
    }

    public KeyPairDTO getNextServerEncKey() {
        return nextServerEncKey;
    }

    public void setNextServerEncKey(KeyPairDTO nextServerEncKey) {
        this.nextServerEncKey = nextServerEncKey;
    }

    public DeviceTypeDTO getType() {
        return type;
    }

    public void setType(DeviceTypeDTO type) {
        this.type = type;
    }

    public OrgUnitDTO getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDTO orgUnit) {
        this.orgUnit = orgUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceDTO)) {
            return false;
        }

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + getId() +
            ", visualId='" + getVisualId() + "'" +
            ", description='" + getDescription() + "'" +
            ", enrollmentCode='" + getEnrollmentCode() + "'" +
            ", enrollmentTime='" + getEnrollmentTime() + "'" +
            ", stateFieldValue01='" + getStateFieldValue01() + "'" +
            ", stateFieldValue02='" + getStateFieldValue02() + "'" +
            ", stateFieldValue03='" + getStateFieldValue03() + "'" +
            ", stateFieldValue04='" + getStateFieldValue04() + "'" +
            ", lastError='" + getLastError() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            ", deviceSignKey=" + getDeviceSignKey() +
            ", deviceEncKey=" + getDeviceEncKey() +
            ", serverSignKey=" + getServerSignKey() +
            ", serverEncKey=" + getServerEncKey() +
            ", nextServerSignKey=" + getNextServerSignKey() +
            ", nextServerEncKey=" + getNextServerEncKey() +
            ", type=" + getType() +
            ", orgUnit=" + getOrgUnit() +
            "}";
    }
}
