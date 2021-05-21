package hu.noreg.iotgw2.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link hu.noreg.iotgw2.domain.Message} entity.
 */
public class MessageDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime serverTime;

    private ZonedDateTime deviceTime;

    /**
     * Hash of the message. lower case hex string
     */
    @NotNull
    @ApiModelProperty(value = "Hash of the message. lower case hex string", required = true)
    private String rawMessageSHA256;

    /**
     * Optional original encrypted message
     */
    @ApiModelProperty(value = "Optional original encrypted message")
    @Lob
    private byte[] rawMessage;

    private String rawMessageContentType;

    @Lob
    private String decryptedPayload;

    @NotNull
    private Boolean devToSrv;

    private String indexFieldValue01;

    private String indexFieldValue02;

    private String indexFieldValue03;

    private String indexFieldValue04;

    @Lob
    private String processingError;

    private MessageTypeDTO type;

    private DeviceDTO device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(ZonedDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public ZonedDateTime getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(ZonedDateTime deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getRawMessageSHA256() {
        return rawMessageSHA256;
    }

    public void setRawMessageSHA256(String rawMessageSHA256) {
        this.rawMessageSHA256 = rawMessageSHA256;
    }

    public byte[] getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(byte[] rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getRawMessageContentType() {
        return rawMessageContentType;
    }

    public void setRawMessageContentType(String rawMessageContentType) {
        this.rawMessageContentType = rawMessageContentType;
    }

    public String getDecryptedPayload() {
        return decryptedPayload;
    }

    public void setDecryptedPayload(String decryptedPayload) {
        this.decryptedPayload = decryptedPayload;
    }

    public Boolean getDevToSrv() {
        return devToSrv;
    }

    public void setDevToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
    }

    public String getIndexFieldValue01() {
        return indexFieldValue01;
    }

    public void setIndexFieldValue01(String indexFieldValue01) {
        this.indexFieldValue01 = indexFieldValue01;
    }

    public String getIndexFieldValue02() {
        return indexFieldValue02;
    }

    public void setIndexFieldValue02(String indexFieldValue02) {
        this.indexFieldValue02 = indexFieldValue02;
    }

    public String getIndexFieldValue03() {
        return indexFieldValue03;
    }

    public void setIndexFieldValue03(String indexFieldValue03) {
        this.indexFieldValue03 = indexFieldValue03;
    }

    public String getIndexFieldValue04() {
        return indexFieldValue04;
    }

    public void setIndexFieldValue04(String indexFieldValue04) {
        this.indexFieldValue04 = indexFieldValue04;
    }

    public String getProcessingError() {
        return processingError;
    }

    public void setProcessingError(String processingError) {
        this.processingError = processingError;
    }

    public MessageTypeDTO getType() {
        return type;
    }

    public void setType(MessageTypeDTO type) {
        this.type = type;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", serverTime='" + getServerTime() + "'" +
            ", deviceTime='" + getDeviceTime() + "'" +
            ", rawMessageSHA256='" + getRawMessageSHA256() + "'" +
            ", rawMessage='" + getRawMessage() + "'" +
            ", decryptedPayload='" + getDecryptedPayload() + "'" +
            ", devToSrv='" + getDevToSrv() + "'" +
            ", indexFieldValue01='" + getIndexFieldValue01() + "'" +
            ", indexFieldValue02='" + getIndexFieldValue02() + "'" +
            ", indexFieldValue03='" + getIndexFieldValue03() + "'" +
            ", indexFieldValue04='" + getIndexFieldValue04() + "'" +
            ", processingError='" + getProcessingError() + "'" +
            ", type=" + getType() +
            ", device=" + getDevice() +
            "}";
    }
}
