package hu.noreg.iotgw2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "iot_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "server_time", nullable = false)
    private ZonedDateTime serverTime;

    @Column(name = "device_time")
    private ZonedDateTime deviceTime;

    /**
     * Hash of the message. lower case hex string
     */
    @NotNull
    @Column(name = "raw_message_sha_256", nullable = false)
    private String rawMessageSHA256;

    /**
     * Optional original encrypted message
     */
    @Lob
    @Column(name = "raw_message")
    private byte[] rawMessage;

    @Column(name = "raw_message_content_type")
    private String rawMessageContentType;

    @Lob
    @Column(name = "decrypted_payload")
    private String decryptedPayload;

    @NotNull
    @Column(name = "dev_to_srv", nullable = false)
    private Boolean devToSrv;

    @Column(name = "index_field_value_01")
    private String indexFieldValue01;

    @Column(name = "index_field_value_02")
    private String indexFieldValue02;

    @Column(name = "index_field_value_03")
    private String indexFieldValue03;

    @Column(name = "index_field_value_04")
    private String indexFieldValue04;

    @Lob
    @Column(name = "processing_error")
    private String processingError;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "messageProcessor", "timeoutProcessor", "deviceTypes" }, allowSetters = true)
    private MessageType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "deviceSignKey", "deviceEncKey", "serverSignKey", "serverEncKey", "nextServerSignKey", "nextServerEncKey", "type", "orgUnit",
        },
        allowSetters = true
    )
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getServerTime() {
        return this.serverTime;
    }

    public Message serverTime(ZonedDateTime serverTime) {
        this.serverTime = serverTime;
        return this;
    }

    public void setServerTime(ZonedDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public ZonedDateTime getDeviceTime() {
        return this.deviceTime;
    }

    public Message deviceTime(ZonedDateTime deviceTime) {
        this.deviceTime = deviceTime;
        return this;
    }

    public void setDeviceTime(ZonedDateTime deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getRawMessageSHA256() {
        return this.rawMessageSHA256;
    }

    public Message rawMessageSHA256(String rawMessageSHA256) {
        this.rawMessageSHA256 = rawMessageSHA256;
        return this;
    }

    public void setRawMessageSHA256(String rawMessageSHA256) {
        this.rawMessageSHA256 = rawMessageSHA256;
    }

    public byte[] getRawMessage() {
        return this.rawMessage;
    }

    public Message rawMessage(byte[] rawMessage) {
        this.rawMessage = rawMessage;
        return this;
    }

    public void setRawMessage(byte[] rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getRawMessageContentType() {
        return this.rawMessageContentType;
    }

    public Message rawMessageContentType(String rawMessageContentType) {
        this.rawMessageContentType = rawMessageContentType;
        return this;
    }

    public void setRawMessageContentType(String rawMessageContentType) {
        this.rawMessageContentType = rawMessageContentType;
    }

    public String getDecryptedPayload() {
        return this.decryptedPayload;
    }

    public Message decryptedPayload(String decryptedPayload) {
        this.decryptedPayload = decryptedPayload;
        return this;
    }

    public void setDecryptedPayload(String decryptedPayload) {
        this.decryptedPayload = decryptedPayload;
    }

    public Boolean getDevToSrv() {
        return this.devToSrv;
    }

    public Message devToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
        return this;
    }

    public void setDevToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
    }

    public String getIndexFieldValue01() {
        return this.indexFieldValue01;
    }

    public Message indexFieldValue01(String indexFieldValue01) {
        this.indexFieldValue01 = indexFieldValue01;
        return this;
    }

    public void setIndexFieldValue01(String indexFieldValue01) {
        this.indexFieldValue01 = indexFieldValue01;
    }

    public String getIndexFieldValue02() {
        return this.indexFieldValue02;
    }

    public Message indexFieldValue02(String indexFieldValue02) {
        this.indexFieldValue02 = indexFieldValue02;
        return this;
    }

    public void setIndexFieldValue02(String indexFieldValue02) {
        this.indexFieldValue02 = indexFieldValue02;
    }

    public String getIndexFieldValue03() {
        return this.indexFieldValue03;
    }

    public Message indexFieldValue03(String indexFieldValue03) {
        this.indexFieldValue03 = indexFieldValue03;
        return this;
    }

    public void setIndexFieldValue03(String indexFieldValue03) {
        this.indexFieldValue03 = indexFieldValue03;
    }

    public String getIndexFieldValue04() {
        return this.indexFieldValue04;
    }

    public Message indexFieldValue04(String indexFieldValue04) {
        this.indexFieldValue04 = indexFieldValue04;
        return this;
    }

    public void setIndexFieldValue04(String indexFieldValue04) {
        this.indexFieldValue04 = indexFieldValue04;
    }

    public String getProcessingError() {
        return this.processingError;
    }

    public Message processingError(String processingError) {
        this.processingError = processingError;
        return this;
    }

    public void setProcessingError(String processingError) {
        this.processingError = processingError;
    }

    public MessageType getType() {
        return this.type;
    }

    public Message type(MessageType messageType) {
        this.setType(messageType);
        return this;
    }

    public void setType(MessageType messageType) {
        this.type = messageType;
    }

    public Device getDevice() {
        return this.device;
    }

    public Message device(Device device) {
        this.setDevice(device);
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", serverTime='" + getServerTime() + "'" +
            ", deviceTime='" + getDeviceTime() + "'" +
            ", rawMessageSHA256='" + getRawMessageSHA256() + "'" +
            ", rawMessage='" + getRawMessage() + "'" +
            ", rawMessageContentType='" + getRawMessageContentType() + "'" +
            ", decryptedPayload='" + getDecryptedPayload() + "'" +
            ", devToSrv='" + getDevToSrv() + "'" +
            ", indexFieldValue01='" + getIndexFieldValue01() + "'" +
            ", indexFieldValue02='" + getIndexFieldValue02() + "'" +
            ", indexFieldValue03='" + getIndexFieldValue03() + "'" +
            ", indexFieldValue04='" + getIndexFieldValue04() + "'" +
            ", processingError='" + getProcessingError() + "'" +
            "}";
    }
}
