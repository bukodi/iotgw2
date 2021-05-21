package hu.noreg.iotgw2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MessageType.
 */
@Entity
@Table(name = "iot_msg_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MessageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type_code", nullable = false)
    private Integer typeCode;

    @NotNull
    @Column(name = "dev_to_srv", nullable = false)
    private Boolean devToSrv;

    /**
     * Unique name of the message type
     */
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Optional description
     */
    @Column(name = "description")
    private String description;

    /**
     * Minimum frequency for Dev->Srv and Sending timeout for Srv->Dev
     */
    @Column(name = "timeout")
    private Duration timeout;

    @Column(name = "retention_time")
    private Duration retentionTime;

    @Column(name = "index_field_name_01")
    private String indexFieldName01;

    @Column(name = "index_field_name_02")
    private String indexFieldName02;

    @Column(name = "index_field_name_03")
    private String indexFieldName03;

    @Column(name = "index_field_name_04")
    private String indexFieldName04;

    @Column(name = "read_auth_pattern")
    private String readAuthPattern;

    @Column(name = "write_auth_pattern")
    private String writeAuthPattern;

    @ManyToOne
    private Processor messageProcessor;

    @ManyToOne
    private Processor timeoutProcessor;

    @ManyToMany(mappedBy = "messageTypes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enrollProcessor", "messageTypes" }, allowSetters = true)
    private Set<DeviceType> deviceTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getTypeCode() {
        return this.typeCode;
    }

    public MessageType typeCode(Integer typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Boolean getDevToSrv() {
        return this.devToSrv;
    }

    public MessageType devToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
        return this;
    }

    public void setDevToSrv(Boolean devToSrv) {
        this.devToSrv = devToSrv;
    }

    public String getName() {
        return this.name;
    }

    public MessageType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public MessageType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getTimeout() {
        return this.timeout;
    }

    public MessageType timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getRetentionTime() {
        return this.retentionTime;
    }

    public MessageType retentionTime(Duration retentionTime) {
        this.retentionTime = retentionTime;
        return this;
    }

    public void setRetentionTime(Duration retentionTime) {
        this.retentionTime = retentionTime;
    }

    public String getIndexFieldName01() {
        return this.indexFieldName01;
    }

    public MessageType indexFieldName01(String indexFieldName01) {
        this.indexFieldName01 = indexFieldName01;
        return this;
    }

    public void setIndexFieldName01(String indexFieldName01) {
        this.indexFieldName01 = indexFieldName01;
    }

    public String getIndexFieldName02() {
        return this.indexFieldName02;
    }

    public MessageType indexFieldName02(String indexFieldName02) {
        this.indexFieldName02 = indexFieldName02;
        return this;
    }

    public void setIndexFieldName02(String indexFieldName02) {
        this.indexFieldName02 = indexFieldName02;
    }

    public String getIndexFieldName03() {
        return this.indexFieldName03;
    }

    public MessageType indexFieldName03(String indexFieldName03) {
        this.indexFieldName03 = indexFieldName03;
        return this;
    }

    public void setIndexFieldName03(String indexFieldName03) {
        this.indexFieldName03 = indexFieldName03;
    }

    public String getIndexFieldName04() {
        return this.indexFieldName04;
    }

    public MessageType indexFieldName04(String indexFieldName04) {
        this.indexFieldName04 = indexFieldName04;
        return this;
    }

    public void setIndexFieldName04(String indexFieldName04) {
        this.indexFieldName04 = indexFieldName04;
    }

    public String getReadAuthPattern() {
        return this.readAuthPattern;
    }

    public MessageType readAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
        return this;
    }

    public void setReadAuthPattern(String readAuthPattern) {
        this.readAuthPattern = readAuthPattern;
    }

    public String getWriteAuthPattern() {
        return this.writeAuthPattern;
    }

    public MessageType writeAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
        return this;
    }

    public void setWriteAuthPattern(String writeAuthPattern) {
        this.writeAuthPattern = writeAuthPattern;
    }

    public Processor getMessageProcessor() {
        return this.messageProcessor;
    }

    public MessageType messageProcessor(Processor processor) {
        this.setMessageProcessor(processor);
        return this;
    }

    public void setMessageProcessor(Processor processor) {
        this.messageProcessor = processor;
    }

    public Processor getTimeoutProcessor() {
        return this.timeoutProcessor;
    }

    public MessageType timeoutProcessor(Processor processor) {
        this.setTimeoutProcessor(processor);
        return this;
    }

    public void setTimeoutProcessor(Processor processor) {
        this.timeoutProcessor = processor;
    }

    public Set<DeviceType> getDeviceTypes() {
        return this.deviceTypes;
    }

    public MessageType deviceTypes(Set<DeviceType> deviceTypes) {
        this.setDeviceTypes(deviceTypes);
        return this;
    }

    public MessageType addDeviceTypes(DeviceType deviceType) {
        this.deviceTypes.add(deviceType);
        deviceType.getMessageTypes().add(this);
        return this;
    }

    public MessageType removeDeviceTypes(DeviceType deviceType) {
        this.deviceTypes.remove(deviceType);
        deviceType.getMessageTypes().remove(this);
        return this;
    }

    public void setDeviceTypes(Set<DeviceType> deviceTypes) {
        if (this.deviceTypes != null) {
            this.deviceTypes.forEach(i -> i.removeMessageTypes(this));
        }
        if (deviceTypes != null) {
            deviceTypes.forEach(i -> i.addMessageTypes(this));
        }
        this.deviceTypes = deviceTypes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageType)) {
            return false;
        }
        return id != null && id.equals(((MessageType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageType{" +
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
            "}";
    }
}
