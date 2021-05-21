package hu.noreg.iotgw2.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.MessageType} entity.
 */
public class MessageTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer typeCode;

    @NotNull
    private Boolean devToSrv;

    /**
     * Unique name of the message type
     */
    @NotNull
    @ApiModelProperty(value = "Unique name of the message type", required = true)
    private String name;

    /**
     * Optional description
     */
    @ApiModelProperty(value = "Optional description")
    private String description;

    /**
     * Minimum frequency for Dev->Srv and Sending timeout for Srv->Dev
     */
    @ApiModelProperty(value = "Minimum frequency for Dev->Srv and Sending timeout for Srv->Dev")
    private Duration timeout;

    private Duration retentionTime;

    private String indexFieldName01;

    private String indexFieldName02;

    private String indexFieldName03;

    private String indexFieldName04;

    private String readAuthPattern;

    private String writeAuthPattern;

    private ProcessorDTO messageProcessor;

    private ProcessorDTO timeoutProcessor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Boolean getDevToSrv() {
        return devToSrv;
    }

    public void setDevToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
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

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Duration retentionTime) {
        this.retentionTime = retentionTime;
    }

    public String getIndexFieldName01() {
        return indexFieldName01;
    }

    public void setIndexFieldName01(String indexFieldName01) {
        this.indexFieldName01 = indexFieldName01;
    }

    public String getIndexFieldName02() {
        return indexFieldName02;
    }

    public void setIndexFieldName02(String indexFieldName02) {
        this.indexFieldName02 = indexFieldName02;
    }

    public String getIndexFieldName03() {
        return indexFieldName03;
    }

    public void setIndexFieldName03(String indexFieldName03) {
        this.indexFieldName03 = indexFieldName03;
    }

    public String getIndexFieldName04() {
        return indexFieldName04;
    }

    public void setIndexFieldName04(String indexFieldName04) {
        this.indexFieldName04 = indexFieldName04;
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

    public ProcessorDTO getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(ProcessorDTO messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public ProcessorDTO getTimeoutProcessor() {
        return timeoutProcessor;
    }

    public void setTimeoutProcessor(ProcessorDTO timeoutProcessor) {
        this.timeoutProcessor = timeoutProcessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageTypeDTO)) {
            return false;
        }

        MessageTypeDTO messageTypeDTO = (MessageTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageTypeDTO{" +
            "id=" + getId() +
            ", typeCode=" + getTypeCode() +
            ", devToSrv='" + getDevToSrv() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", timeout='" + getTimeout() + "'" +
            ", retentionTime='" + getRetentionTime() + "'" +
            ", indexFieldName01='" + getIndexFieldName01() + "'" +
            ", indexFieldName02='" + getIndexFieldName02() + "'" +
            ", indexFieldName03='" + getIndexFieldName03() + "'" +
            ", indexFieldName04='" + getIndexFieldName04() + "'" +
            ", readAuthPattern='" + getReadAuthPattern() + "'" +
            ", writeAuthPattern='" + getWriteAuthPattern() + "'" +
            ", messageProcessor=" + getMessageProcessor() +
            ", timeoutProcessor=" + getTimeoutProcessor() +
            "}";
    }
}
