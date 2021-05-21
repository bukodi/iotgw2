package hu.noreg.iotgw2.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.DeviceType} entity.
 */
public class DeviceTypeDTO implements Serializable {

    private Long id;

    /**
     * Unique name of the device type
     */
    @NotNull
    @ApiModelProperty(value = "Unique name of the device type", required = true)
    private String name;

    /**
     * Optional description
     */
    @ApiModelProperty(value = "Optional description")
    private String description;

    private String stateFieldName01;

    private String stateFieldName02;

    private String stateFieldName03;

    private String stateFieldName04;

    private String readAuthPattern;

    private String writeAuthPattern;

    private ProcessorDTO enrollProcessor;

    private Set<MessageTypeDTO> messageTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStateFieldName01() {
        return stateFieldName01;
    }

    public void setStateFieldName01(String stateFieldName01) {
        this.stateFieldName01 = stateFieldName01;
    }

    public String getStateFieldName02() {
        return stateFieldName02;
    }

    public void setStateFieldName02(String stateFieldName02) {
        this.stateFieldName02 = stateFieldName02;
    }

    public String getStateFieldName03() {
        return stateFieldName03;
    }

    public void setStateFieldName03(String stateFieldName03) {
        this.stateFieldName03 = stateFieldName03;
    }

    public String getStateFieldName04() {
        return stateFieldName04;
    }

    public void setStateFieldName04(String stateFieldName04) {
        this.stateFieldName04 = stateFieldName04;
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

    public ProcessorDTO getEnrollProcessor() {
        return enrollProcessor;
    }

    public void setEnrollProcessor(ProcessorDTO enrollProcessor) {
        this.enrollProcessor = enrollProcessor;
    }

    public Set<MessageTypeDTO> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(Set<MessageTypeDTO> messageTypes) {
        this.messageTypes = messageTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceTypeDTO)) {
            return false;
        }

        DeviceTypeDTO deviceTypeDTO = (DeviceTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", stateFieldName01='" + getStateFieldName01() + "'" +
            ", stateFieldName02='" + getStateFieldName02() + "'" +
            ", stateFieldName03='" + getStateFieldName03() + "'" +
            ", stateFieldName04='" + getStateFieldName04() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            ", enrollProcessor=" + getEnrollProcessor() +
            ", messageTypes=" + getMessageTypes() +
            "}";
    }
}
